package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.user.DAO.CategoryDAO;
import kr.co.sist.user.Service.CategoryService;
import kr.co.sist.user.Service.SearchItemService;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchItemService service;
	
    // 새로 추가
    @Autowired
    private CategoryService categoryService;
	
	@GetMapping
	public String search(Model model, @RequestParam(name="keyword", required=false, defaultValue="") String keyword,
			@RequestParam(name="catNum",  required=false) Integer catNum) {
      //  List<ProductDTO> results = service.findByKeyword(keyword);
        List<ProductDTO> results;
        String title="";
        if (catNum != null) {
            // catNum이 넘어오면 카테고리 검색
            results = service.findByCategory(catNum);
            
            String categoryName = service.getCategoryName(catNum);
            model.addAttribute("categoryName", categoryName);
            title = categoryName;
        } else {
            // keyword로 기존 검색
            results = service.findByKeyword(keyword);
            title = (keyword.isBlank() ? "전체" : keyword);
        }
        
        model.addAttribute("results", results);
        model.addAttribute("count",   results.size());
      //  model.addAttribute("keyword", keyword);
        model.addAttribute("catNum", catNum);
        model.addAttribute("title", title);
      return "user/search";
   }//search
	
   
}//class