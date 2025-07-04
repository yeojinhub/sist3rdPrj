package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.DTO.CompanyWithProductDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.PlaningService;

@Controller
public class PlaningController {

	@Autowired
	private PlaningService planingService;
	
	@GetMapping("/planingsell")
	public String showPlaningName(
			@RequestParam (value = "comNum", required = false) String comNum,
			Model model) {
		
		// 1. 기업 리스트 조회
		List<CompanyWithProductDTO> companies = planingService.getCompanyNames();
		model.addAttribute("listNames",companies);
		
		// 2. 선택된 comNum이 없으면 첫 기업으로 설정
        if (comNum == null && !companies.isEmpty()) {
            comNum = companies.get(0).getComNum();
        }
        model.addAttribute("selectedComNum", comNum);
        
        // 3. 해당 기업의 상품 리스트 조회
        List<ProductDTO> products = planingService.getProductListByComNum(comNum);
        model.addAttribute("products", products);
		
		return "user/planingsell";
	}
	
	@GetMapping("/planingDetail")
	public String planingDetail(@RequestParam("id") int prodcutId) {
		return "user/product/planingDetail";
	}
	
	
}
