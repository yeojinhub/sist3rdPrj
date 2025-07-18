package kr.co.sist.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.admin.Service.AdminPlaningService;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.PlaningPaginationDTO;

@Controller
@RequestMapping("/admin/planing")
public class AdminPlaningContorller {

	@Autowired
	private AdminPlaningService adminPlaningService;
	
	@GetMapping("/planingList")
	public String planing(@RequestParam(defaultValue = "abc123") String id,
		    @RequestParam(defaultValue = "1") int page,
		    @RequestParam(defaultValue = "") String keyword,
		    @RequestParam(defaultValue = "all") String hidden, // all, Y, N
		    Model model) {
		
		// 1. comNum 조회
	    String comNum = adminPlaningService.getComNumById(id);

	    // 2. 상품 리스트 + pagination 처리
	    int pageSize = 10;
	    int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        List<ProductDTO> products = adminPlaningService.searchProductsByCondition(comNum, keyword, hidden, startRow, endRow);
        int totalCount = adminPlaningService.getTotalCountByCondition(comNum, keyword, hidden);
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
	public String PlanningAdd(Model model) {
		
		List<CategoryDTO> categories = adminPlaningService.getAllCategories();
	    model.addAttribute("categories", categories);
	    model.addAttribute("productDTO", new ProductDTO());  // 폼 바인딩용
        model.addAttribute("imageDTO", new ImageDTO()); //이미지 바인
		
		return "admin/planing/planingAdd";
	}
	

	@PostMapping(value = "/planingAdd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> savePlanning(
	        @RequestPart("product") ProductDTO productDTO,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images   // null 가능
	) {

	    String prdNum = null;
	    File   uploadFolder = null;

	    try {
	        // ── 1. 시퀀스 구하기 ─────────────────────────
	        int prdSeq = adminPlaningService.getNextProductSeq();
	        int imgSeq = adminPlaningService.getNextImageSeq();
	        prdNum = String.format("P_%08d", prdSeq);

	        // ── 2. 업로드 폴더 준비 ─────────────────────
	        String basePath = new File("src/main/resources/static/images/product/upload").getAbsolutePath();
	        uploadFolder    = new File(basePath, prdNum);
	        if (!uploadFolder.exists()) uploadFolder.mkdirs();

	        // ── 3. ImageDTO 채우기 ──────────────────────
	        ImageDTO imageDTO = new ImageDTO();
	        imageDTO.setImgNum(imgSeq);
	        imageDTO.setImageType("1");        // 관리자 상품 고정

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

	        // ── 4. ProductDTO 채우기 ────────────────────
	        productDTO.setImgNum(imgSeq);
	        productDTO.setPrdNum(prdNum);
	        productDTO.setComNum("1");        // 관리자 계정
	        productDTO.setUserNum("1");        // 관리자 계정

	        // ── 5. 저장(트랜잭션) ───────────────────────
	        adminPlaningService.insertProductWithImages(productDTO, imageDTO);

	        return ResponseEntity.ok().build();

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        
	        if (uploadFolder != null && uploadFolder.exists()) deleteFolder(uploadFolder);

	        return ResponseEntity
	               .badRequest()
	               .body("등록 실패: " + ex.getMessage());
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
	
	
}
