package kr.co.sist.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.user.DAO.CategoryDAO;
import kr.co.sist.user.Service.CategoryService;
import kr.co.sist.user.Service.ProductService;
import kr.co.sist.user.Service.SearchItemService;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private SearchItemService serchService;
	
    @Autowired
    private CategoryService categoryService;
	
	@GetMapping
	public String search(Model model, @RequestParam(name="keyword", required=false, defaultValue="") String keyword,
			@RequestParam(name="catNum",  required=false) Integer catNum,
			@RequestParam(name="minPrice", required=false) Integer minPrice,
			@RequestParam(name="maxPrice", required=false) Integer maxPrice,
			@RequestParam(name="tradeOption",required=false, defaultValue="전체") String tradeOption,
			@RequestParam(name="sortOption", required=false, defaultValue="추천순") String sortOption) {
		//  @PathVariable annotation  - 맨마지막의 값을 얻어서 사용함 (day0708)
		//  parameter에 정의하면 특정 부분의 값을 얻을 수 있다.
		
		//  List<ProductDTO> results = service.findByKeyword(keyword);
		//  List<ProductDTO> results;
        
        // 일반 검색 결과
	    List<ProductDTO> results = serchService.findByKeywordAndFilters(
	            keyword, catNum, minPrice, maxPrice, tradeOption, sortOption
	        );

	    List<ImageDTO> resultsImage = new ArrayList<ImageDTO>();
	    for (ProductDTO p : results) {
	    	resultsImage.add(productService.selectImageByNum(p.getImgNum()));
	    }
	    
	    model.addAttribute("resultsImage",resultsImage);
	    
	    // 광고 상품 (카테고리 기준)
	    ProductDTO adItem=null;
	    if (catNum != null) {
            // catNum이 넘어오면 카테고리 검색
	    	adItem = serchService.getTopAdminScoreItem(catNum);
	    }
	    
	    // 타이틀 결정
	    String title="";
        if (catNum != null) {
            // catNum이 넘어오면 카테고리 검색
        	title = categoryService.getCategoryName(catNum);
        } else if ( !keyword.isBlank() ) {
        	title = keyword;
        } else {
        	title="전체";
        }
        
        model.addAttribute("adItem", adItem);
        model.addAttribute("results", results);
        model.addAttribute("count",   results.size());
        model.addAttribute("keyword", keyword); 
        model.addAttribute("catNum", catNum);
        model.addAttribute("title", title);
        // 정렬 옵션
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("tradeOption", tradeOption);
        model.addAttribute("sortOption", sortOption);
        
      return "user/search";

   }//search
	
   
}//class