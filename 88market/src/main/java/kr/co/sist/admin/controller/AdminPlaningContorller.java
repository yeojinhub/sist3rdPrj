package kr.co.sist.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.OrderManageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.AdminDTO;
import kr.co.sist.admin.Service.AdminPlaningService;
import kr.co.sist.admin.util.PlaningPaginationDTO;

@Controller
@RequestMapping("/admin/planing")
public class AdminPlaningContorller {

    @Autowired
    private AdminPlaningService adminPlaningService;

    @GetMapping("/planingList")
    public String planing(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "") String keyword,
                          @RequestParam(defaultValue = "all") String hidden,
                          Model model,
                          HttpSession session) {

        String accountType = (String) session.getAttribute("loggedInAccountType");
        String id = (String) session.getAttribute("loginId");

        if (id == null) return "redirect:/admin/login";

        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        int totalCount = 0;
        List<ProductDTO> products;

        if ("ADMIN".equals(accountType)) {
            products = adminPlaningService.searchAllProducts(keyword, hidden, startRow, endRow);
            totalCount = adminPlaningService.getAllProductCount(keyword, hidden);
        } else {
            String comNum = adminPlaningService.getComNumById(id);
            products = adminPlaningService.searchProductsByCondition(comNum, keyword, hidden, startRow, endRow);
            totalCount = adminPlaningService.getTotalCountByCondition(comNum, keyword, hidden);
        }

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        PlaningPaginationDTO pagination = new PlaningPaginationDTO(page, pageSize, totalCount);

        model.addAttribute("productsList", products);
        model.addAttribute("pagination", Map.of(
                "pageNum", page,
                "pageSize", pageSize,
                "totalCount", totalCount,
                "totalPages", totalPages
        ));
        model.addAttribute("param", Map.of(
                "keyword", keyword,
                "hidden", hidden
        ));

        return "admin/planing/planingList";
    }

    @GetMapping("/planingAdd")
    public String PlanningAdd(Model model, HttpSession session) {
        String id = (String) session.getAttribute("loginId");
        if (id == null) return "redirect:/admin/login";

        List<CategoryDTO> categories = adminPlaningService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("imageDTO", new ImageDTO());

        return "admin/planing/planingAdd";
    }

    @PostMapping(value = "/planingAdd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> savePlanning(@RequestPart("product") ProductDTO productDTO,
                                          @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                          HttpSession session) {

        String id = (String) session.getAttribute("loginId");
        if (id == null) return ResponseEntity.status(401).body("로그인 필요");

        String comNum = adminPlaningService.getComNumById(id);
        String prdNum = null;
        File uploadFolder = null;

        try {
            int prdSeq = adminPlaningService.getNextProductSeq();
            int imgSeq = adminPlaningService.getNextImageSeq();
            prdNum = String.format("P_%08d", prdSeq);

            String basePath = new File("src/main/resources/static/images/product/upload").getAbsolutePath();
            uploadFolder = new File(basePath, prdNum);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setImgNum(imgSeq);
            imageDTO.setImageType(comNum);

            if (images != null) {
                for (int i = 0; i < images.size(); i++) {
                    String saved = saveFileWithFixedName(images.get(i), uploadFolder, prdNum, i);
                    switch (i) {
                        case 0 -> imageDTO.setMainImage(saved);
                        case 1 -> imageDTO.setSubImage1(saved);
                        case 2 -> imageDTO.setSubImage2(saved);
                        case 3 -> imageDTO.setSubImage3(saved);
                        case 4 -> imageDTO.setSubImage4(saved);
                    }
                }
            }

            productDTO.setImgNum(imgSeq);
            productDTO.setPrdNum(prdNum);
            productDTO.setComNum(comNum);

            adminPlaningService.insertProductWithImages(productDTO, imageDTO);

            return ResponseEntity.ok().build();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (uploadFolder != null && uploadFolder.exists()) deleteFolder(uploadFolder);
            return ResponseEntity.badRequest().body("등록 실패: " + ex.getMessage());
        }
    }

    private String saveFileWithFixedName(MultipartFile file, File folder, String prdNum, int index) throws IOException {
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String saveName = prdNum + "_" + (index + 1) + ext;
        File dest = new File(folder, saveName);
        file.transferTo(dest);
        return "/images/product/upload/" + prdNum + "/" + saveName;
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

    @GetMapping("/planingDetail")
    public String detail(@RequestParam("prdNum") String prdNum, Model model, HttpSession session) {
        String id = (String) session.getAttribute("loginId");
        if (id == null) return "redirect:/admin/login";

        ProductDTO product = adminPlaningService.getProductByPrdNum(prdNum);
        ImageDTO image = null;
        if (product != null && product.getImgNum() != 0) {
            image = adminPlaningService.getImageByImgNum(product.getImgNum());
        }

        List<CategoryDTO> categories = adminPlaningService.getAllCategories();

        model.addAttribute("productDTO", product);
        model.addAttribute("categories", categories);
        model.addAttribute("imageDTO", image);

        return "admin/planing/planingDetail";
    }

    @PostMapping(value = "/planingDetail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateProduct(@RequestPart("product") ProductDTO productDTO,
                                           @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            adminPlaningService.updateProductWithImages(productDTO, images);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 실패");
        }
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("prdNums") List<String> prdNums) {
        for (String prdNum : prdNums) {
            adminPlaningService.deleteProductByPrdNum(prdNum);
        }
        return "redirect:/admin/planing/planingList";
    }

    @GetMapping("/planingOrderList")
    public String planingOrderList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String keyword,
                                    @RequestParam(defaultValue = "all") String tradeStatus,
                                    Model model,
                                    HttpSession session) {

        String id = (String) session.getAttribute("loginId");
        if (id == null) return "redirect:/admin/login";

        // 세션에서 로그인한 계정 유형 가져오기 (ADMIN 또는 SELLER)
        String accountType = (String) session.getAttribute("loggedInAccountType");

        // 기업일 경우 comNum 필요 (관리자면 null이어도 상관없음)
        String comNum = adminPlaningService.getComNumById(id);

        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        List<OrderManageDTO> orderList = adminPlaningService.getOrderList(accountType, comNum, keyword, tradeStatus, startRow, endRow);
        int totalCount = adminPlaningService.getOrderCount(accountType, comNum, keyword, tradeStatus);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("orderList", orderList);
        model.addAttribute("pagination", Map.of(
                "pageNum", page,
                "pageSize", pageSize,
                "totalCount", totalCount,
                "totalPages", totalPages
        ));
        model.addAttribute("param", Map.of(
                "keyword", keyword,
                "tradeStatus", tradeStatus
        ));

        return "admin/planing/planingOrderList";
    }


 // 주문 상세 조회
    @GetMapping("/planingOrderDetail")
    public String planingOrderDetail(@RequestParam("tradeId") String tradeId,
                                     Model model, HttpSession session) {
        String id = (String) session.getAttribute("loginId");
        if (id ==  null) return "redirect:/admin/login";

        OrderManageDTO trade = adminPlaningService.getOrderDetail(tradeId);
        
        if (trade == null) {
            return "redirect:/admin/planing/planingOrderList"; // 없으면 리스트로
        }
        
        model.addAttribute("trade", trade);

        return "admin/planing/planingOrderDetail";
    }

    // 거래 상태 수정 처리
    @PostMapping("/planingOrderDetail/update")
    public String updateTradeStatus(@ModelAttribute("trade") OrderManageDTO dto,
                                    RedirectAttributes ra) {

        adminPlaningService.updateTradeStatus(dto);

        ra.addAttribute("tradeId", dto.getTradeId());
        return "redirect:/admin/planing/planingOrderDetail";
    }
}
