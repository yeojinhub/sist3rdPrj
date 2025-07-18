package kr.co.sist.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sist.DTO.AdminUserProductDTO;
import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.admin.Service.AdminUserProductService;
import kr.co.sist.admin.util.AdminSearchUserProductDTO;
import kr.co.sist.admin.util.UserProductPagination;
import kr.co.sist.user.Service.CategoryService;

@Controller
@RequestMapping("/admin/userProduct")
public class AdminUserProductController {
	
  @Autowired 
  private AdminUserProductService service;
  
  // 카테고리 조회용 서비스
  @Autowired
  private CategoryService categoryService;
  
  
  //@GetMapping({"/userProduct", "/userProductList"})
 // public String userProductPage(Model model) {
  //  model.addAttribute("userProductList", service.getUserProducts());
    
	  @GetMapping({"","/list"})
	  public String list(@ModelAttribute("search") AdminSearchUserProductDTO searchDTO,Model model) {
		  
	      //  검색 결과
		  if (searchDTO.getHideFilter() == null || searchDTO.getHideFilter().isEmpty()) {
			  searchDTO.setHideFilter("");   // 전체
		  }
		  if (searchDTO.getStatusFilter() == null || searchDTO.getStatusFilter().isEmpty()) {
			  searchDTO.setStatusFilter("all");
		  }
		  
		  UserProductPagination<AdminUserProductDTO> page = service.findByAdminSearch(searchDTO);
	     
		  model.addAttribute("search", searchDTO);
		  model.addAttribute("catNum", searchDTO.getCatNum());
		  //카테고리 선택 유지
		  model.addAttribute("pagination",  page);
	      model.addAttribute("userProductList", page.getItems());  
	      // 카테고리
	      model.addAttribute("categories", categoryService.getAllCategories());
	      return "admin/userProduct/userProductList";
	  }
  
	  @PostMapping("/hideon")
	  @ResponseBody
	  public ResponseEntity<?> hideOn(@RequestBody Map<String,List<String>> payload) {
	    List<String> prdNums = payload.get("prdNums");
	    service.updateHiddenType(prdNums, true);
	    return ResponseEntity.ok().build();
	  }
	
	  @PostMapping("/hideoff")
	  @ResponseBody
	  public ResponseEntity<Void> hideOff(@RequestBody Map<String,List<String>> payload) {
	    List<String> prdNums = payload.get("prdNums");
	    service.updateHiddenType(prdNums, false);
	    return ResponseEntity.ok().build();
	  }
	  
	  // 상세페이지
	  @GetMapping("/detail/{prdNum}")
	  public String detail(@PathVariable("prdNum") String prdNum, Model model) {
		  AdminUserProductDTO dto = service.findByPrdNum(prdNum);
		  model.addAttribute("product", dto);
		  model.addAttribute("categories", categoryService.getAllCategories());
		  return "admin/userProduct/userProductDetail";
	  }
  
  
}

	


