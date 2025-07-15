package kr.co.sist.user.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.CategoryService;
import kr.co.sist.user.Service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/sell")
    public String sell(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategories());
        return "user/product/sell";
    }

    @GetMapping("/success")
    public String successPage() {
        return "user/product/success";
    }

    @ResponseBody
    @PostMapping("/product/sell")
    public ResponseEntity<?> registerProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        String prdNum = null;
        File uploadFolder = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductDTO product = mapper.readValue(productJson, ProductDTO.class);

            if (product.getDeliveryType() == null) product.setDeliveryType("N");
            if (product.getMeetType() == null) product.setMeetType("N");
            if (product.getSafeType() == null) product.setSafeType("N");

            int prdSeq = productService.getNextProductSeq();
            int imgSeq = productService.getNextImageSeq();
            prdNum = String.format("P_%08d", prdSeq);

            // 이미지 저장 경로 준비
            String basePath = new File("src/main/resources/static/images/product/upload").getAbsolutePath();
            uploadFolder = new File(basePath, prdNum);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            ImageDTO image = new ImageDTO();
            image.setImgNum(imgSeq);
            image.setImageType("1");

            if (images != null) {
                for (int i = 0; i < images.size(); i++) {
                    String savedPath = saveFileWithFixedName(images.get(i), uploadFolder, prdNum, i);
                    switch (i) {
                        case 0 -> image.setMainImage(savedPath);
                        case 1 -> image.setSubImage1(savedPath);
                        case 2 -> image.setSubImage2(savedPath);
                        case 3 -> image.setSubImage3(savedPath);
                        case 4 -> image.setSubImage4(savedPath);
                    }
                }
                product.setImgNum(imgSeq);
            }

            product.setPrdNum(prdNum);
            product.setUserNum("1");

            // 트랜잭션 처리
            productService.insertProductWithImages(product, image);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();

            // 폴더 및 내부 파일 삭제
            if (uploadFolder != null && uploadFolder.exists()) {
                deleteFolder(uploadFolder);
            }

            return ResponseEntity.badRequest().body("등록 실패: " + e.getMessage());
        }
    }
    
    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        folder.delete();
    }

    private String saveFileWithFixedName(MultipartFile file, File folder, String prdNum, int index) throws Exception {
        if (file == null || file.isEmpty()) return null;

        String ext = getFileExtension(file.getOriginalFilename());
        String fileName = switch (index) {
            case 0 -> "main" + ext;
            case 1 -> "sub1" + ext;
            case 2 -> "sub2" + ext;
            case 3 -> "sub3" + ext;
            case 4 -> "sub4" + ext;
            default -> throw new IllegalArgumentException("이미지는 최대 5장까지만 허용됩니다.");
        };

        File dest = new File(folder, fileName);
        file.transferTo(dest);
        return "/images/product/upload/" + prdNum + "/" + fileName;
    }

    private String getFileExtension(String originalFilename) {
        int dotIndex = originalFilename.lastIndexOf(".");
        return (dotIndex >= 0) ? originalFilename.substring(dotIndex) : "";
    }

    @GetMapping("/seller")
    public String seller(@RequestParam("id") int sellerId) {
        return "user/product/seller";
    }
    
    @GetMapping("/detail")
    public String detail(@RequestParam("id") String prdNum, Model model) {
        ProductDTO product = productService.selectProductByNum(prdNum);
        model.addAttribute("product", product);

        ImageDTO image = null;

        // ✅ Null-safe 처리: getImgNum()이 null일 경우 처리 피하기
        if (product != null && product.getImgNum() != null) {
            image = productService.selectImageByNum(product.getImgNum());
        }

        model.addAttribute("image", image);
        return "user/product/detail";
    }


}
