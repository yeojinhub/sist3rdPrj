package kr.co.sist.user.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.CategoryService;
import kr.co.sist.user.Service.ImageService;
import kr.co.sist.user.Service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/detail")
    public String productDetail(@RequestParam("id") int productId) {
        return "user/product/detail";
    }

    @GetMapping("/sell")
    public String sell(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategories());
        return "user/product/sell";
    }
    
    @GetMapping("/product/success")
    public String successPage() {
        return "user/product/success"; 
    }

    @PostMapping("/product/sell")
    public String registerProduct(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("price") int price,
            @RequestParam("location1") String location1,
            @RequestParam("catNum") int catNum,
            @RequestParam(value = "safeType", required = false) String safeType,
            @RequestParam(value = "deliveryType", required = false) String deliveryType,
            @RequestParam(value = "meetType", required = false) String meetType,
            @RequestParam("images") MultipartFile[] images
    ) throws Exception {

        // 임시 값 (로그인 미구현 상태)
        String userNum = "1";

        // 1. 상품 번호 생성
        int prdSeq = productService.getNextProductSeq();
        String prdNum = String.format("P_%04d", prdSeq);

        // 2. 이미지 저장 경로 준비
        String basePath = new File("src/main/resources/static/images/product/upload").getAbsolutePath();
        File prdFolder = new File(basePath, prdNum);
        if (!prdFolder.exists()) prdFolder.mkdirs();

        // 3. 이미지 저장 및 DTO 구성
        ImageDTO image = new ImageDTO();
        image.setImgNum(imageService.getNextImageSeq());
        image.setImageType("1");

        for (int i = 0; i < images.length; i++) {
            String savedPath = saveFile(images[i], prdFolder, prdNum);
            switch (i) {
                case 0 -> image.setMainImage(savedPath);
                case 1 -> image.setSubImage1(savedPath);
                case 2 -> image.setSubImage2(savedPath);
                case 3 -> image.setSubImage3(savedPath);
                case 4 -> image.setSubImage4(savedPath);
            }
        }
        imageService.insertImage(image);

        // 4. 상품 DTO 구성
        ProductDTO product = new ProductDTO();
        product.setPrdNum(prdNum);
        product.setTitle(title);
        product.setContent(content);
        product.setPrice(price);
        product.setLocation1(location1);
        product.setUserNum(userNum);
        product.setCatNum(catNum);
        product.setImgNum(image.getImgNum());

        product.setClickNum(0);
        product.setPrdCnt(1);
        product.setSellType("N");
        product.setHiddenType("N");
        product.setAppointType("N");
        product.setSafeType(safeType != null ? "Y" : "N");
        product.setDeliveryType(deliveryType != null ? "Y" : "N");
        product.setMeetType(meetType != null ? "Y" : "N");

        // 5. 상품 insert
        productService.insertProduct(product);

        return "redirect:/product/success";
    }

    private String saveFile(MultipartFile file, File productFolder, String prdNum) throws Exception {
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File dest = new File(productFolder, fileName);
            file.transferTo(dest);
            return "/images/product/upload/" + prdNum + "/" + fileName;
        }
        return null;
    }

    @GetMapping("/buy")
    public String buy() {
        return "user/product/buy";
    }

    @GetMapping("/seller")
    public String seller(@RequestParam("id") int sellerId) {
        return "user/product/seller";
    }

    @GetMapping("/planingsell")
    public String planingsell() {
        return "user/planingsell";
    }
}


