package kr.co.sist.user.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/buy")
    public String buy() {
        return "user/product/buy";
    }

    @PostMapping("/product/register")
    public String registerProduct(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("price") int price,
            @RequestParam("location1") String location1,
            @RequestParam("userNum") String userNum,
            @RequestParam("catNum") int catNum,
            @RequestParam("comNum") String comNum,
            @RequestParam(value = "prd_safe", required = false) String prdSafe, 
            @RequestParam(value = "prd_delivery", required = false) String prdDelivery,
            @RequestParam(value = "prd_direct", required = false) String prdDirect,
            @RequestParam("images") MultipartFile[] images
    ) throws Exception {

        // 상품 번호 생성
        int prdSeq = productService.getNextProductSeq();
        String prdNum = String.format("P_%04d", prdSeq);

        // 이미지 저장 폴더
        String staticPath = new File("src/main/resources/static/images/upload/product").getAbsolutePath();
        File productFolder = new File(staticPath, prdNum);
        if (!productFolder.exists()) productFolder.mkdirs();

        // 이미지 저장
        ImageDTO image = new ImageDTO();
        image.setImgNum(imageService.getNextImageSeq());
        image.setImageType("1");

        for (int i = 0; i < images.length; i++) {
            String savedPath = saveFile(images[i], productFolder, prdNum);
            if (i == 0) image.setMainImage(savedPath);
            else if (i == 1) image.setSubImage1(savedPath);
            else if (i == 2) image.setSubImage2(savedPath);
            else if (i == 3) image.setSubImage3(savedPath);
            else if (i == 4) image.setSubImage4(savedPath);
        }

        imageService.insertImage(image);

        // 상품 등록
        ProductDTO product = new ProductDTO();
        product.setPrdNum(prdNum);
        product.setTitle(title);
        product.setContent(content);
        product.setPrice(price);
        product.setLocation1(location1);
        product.setUserNum(userNum);
        product.setCatNum(catNum);
        product.setComNum(comNum);
        product.setImgNum(image.getImgNum());
        product.setClickNum(0);
        product.setPrdCnt(1);
        product.setSellType("N");
        product.setHiddenType("N");
        product.setAppointType("N");
        product.setSafeType(prdSafe != null ? "Y" : "N"); 
        product.setDeliveryType(prdDelivery != null ? "Y" : "N"); 
        product.setMeetType(prdDirect != null ? "Y" : "N");       

        productService.insertProduct(product);
        return "redirect:/product/success";
    }

    private String saveFile(MultipartFile file, File productFolder, String prdNum) throws Exception {
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File dest = new File(productFolder, fileName);
            file.transferTo(dest);
            return "/images/upload/product/" + prdNum + "/" + fileName;
        }
        return null;
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

