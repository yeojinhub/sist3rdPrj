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
		//  parameter에 정의하면 특정 부분의 값을 얻을 수 있다. - 로그인한 아이디값이 필요함, 회원/ 상품에 사용하는게 좋을듯
		
		//  List<ProductDTO> results = service.findByKeyword(keyword);
		//  List<ProductDTO> results;
        String title="";
	    List<ProductDTO> results = serchService.findByKeywordAndFilters(
	            keyword, catNum, minPrice, maxPrice, tradeOption, sortOption
	        );
        
	    ProductDTO adDTO=serchService.getTopAdminScoreItem(catNum);
        if (adDTO != null) {
            // catNum이 넘어오면 카테고리 검색
            // results = serchService.findByCategory(catNum);
            results.add(0,adDTO);
            
        } else {
            // keyword로 기존 검색 - 이렇게 하지 않으면 null 생김(서비스에서도 사용하기)
            title = (keyword.isBlank() ? "전체" : keyword);
        }
        
        
         title = (catNum == null
                ? (keyword.isBlank() ? "전체" : keyword)
                : serchService.getCategoryName(catNum));
        
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