package kr.co.sist.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.DTO.CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO;
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
	public String planingDetail(@RequestParam("id") String productId, 
			@RequestParam("comNum") String comNum, Model model) {
		
		// 1. 기업 리스트 조회
		List<CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO> companieProduct = planingService.getCompanyProduct(productId);
		model.addAttribute("Product",companieProduct);
		
//		// 3. 해당 기업의 상품 리스트 조회
//        List<ProductDTO> products = planingService.getProductListByComNum(comNum);
//        model.addAttribute("products", products);
		
		// 2. 현재 상품 제외한 해당 기업의 다른 상품들 (3개 제한)
	    List<ProductDTO> allProducts = planingService.getProductListByComNum(comNum);

	    List<ProductDTO> filteredProducts = allProducts.stream()
	        .filter(p -> !String.valueOf(p.getPrdNum()).equals(productId)) // 현재 상품 제외
	        .limit(3)
	        .collect(Collectors.toList());

	    model.addAttribute("products", filteredProducts);
	    
	 // 같은 기업의 다른 랜덤 상품들 (현재 상품 제외)
	    List<ProductDTO> relatedProducts =
	            planingService.getRandomProductsByComNum(comNum, productId);
	    model.addAttribute("relatedProducts", relatedProducts);
        
		
		return "user/product/planingDetail";
	}
	
	@GetMapping("/planingSeller")
	public String planingSeller(@RequestParam("id") String sellerId,
			@RequestParam("comNum") String comNum,
			@Param("sellType") String sellType,
			Model model) {
		// 기업 리스트 조회
		List<CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO> companieProduct = planingService.getCompanyProduct(sellerId);
		model.addAttribute("Product",companieProduct);
		
		// 해당 기업의 상품 리스트 조회
		List<ProductDTO> products = planingService.getProductListByComNum(comNum);
		model.addAttribute("products", products);
		
		// 해당기업 상품 갯수
		int ProductAllCnt = planingService.getCompanyProductsAllCount(comNum);
		model.addAttribute("ProductAllCnt",ProductAllCnt);
		
		// 해당기업 상품 판매 갯수
		int sellAllCnt = planingService.getCompanyProductsSellCount(comNum);
		model.addAttribute("sellAllCnt",sellAllCnt);
		
		// 해당기업 상품 품절 갯수
		int sellSoldOutCnt = planingService.getCompanyProductsSoldOutCount(comNum);
		model.addAttribute("sellSoldOutCnt",sellSoldOutCnt);
		
		//해당기업 총상품 리스트
		List<ProductDTO> AllProductsByCompany = planingService.AllProductsByCompany(comNum);
		model.addAttribute("AllProductsByCompany",AllProductsByCompany);
		
		//해당기업 판매중 솔드아웃 리스트
		List<ProductDTO> ProductsSellType = planingService.ProductsByCompanyAndSellType(comNum, sellType);
		model.addAttribute("ProductsSellType",ProductsSellType);
		
		return "user/product/planingSeller";
	}
	
	
}
