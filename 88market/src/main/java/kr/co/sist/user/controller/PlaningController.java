package kr.co.sist.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.DTO.CategoryWithProductWithFavoriteWithCompanyWithImageDTO;
import kr.co.sist.DTO.CompanyWithProductDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.ReviewDTO;
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
        List<Map<String, Object>> productsWithImages = planingService.getProductListByComNumWithImage(comNum);
        model.addAttribute("products", productsWithImages);
		
		return "user/planingsell";
	}
	
	@GetMapping("/planingDetail")
	public String planingDetail(@RequestParam("id") String productId, 
			@RequestParam("comNum") String comNum, Model model,
			HttpSession session) {
		
		// 세션에 저장할 key 이름
	    String sessionKey = "viewedProducts";
		
	    //  세션에서 이미 조회한 상품 리스트 가져오기
	    @SuppressWarnings("unchecked")
	    Set<String> viewedProducts = (Set<String>) session.getAttribute(sessionKey);

	    if (viewedProducts == null) {
	        viewedProducts = new HashSet<>();
	    }
	    //  아직 조회하지 않은 상품이면 조회수 증가 + 세션에 저장
	    if (!viewedProducts.contains(productId)) {
	        planingService.addClickCount(productId);  // 조회수 증가 서비스 호출
	        viewedProducts.add(productId);
	        session.setAttribute(sessionKey, viewedProducts);
	    }
	    
		// 기업 리스트 조회
		List<CategoryWithProductWithFavoriteWithCompanyWithImageDTO> companieProduct = planingService.getCompanyProduct(productId);
		model.addAttribute("Product",companieProduct);
		
		// 이미지 파일명 리스트 만들기 (메인 + 서브)
	    if (!companieProduct.isEmpty()) {
	        CategoryWithProductWithFavoriteWithCompanyWithImageDTO product = companieProduct.get(0);
	        List<String> imgFiles = new ArrayList<>();

	        if(product.getMainImage() != null && !product.getMainImage().isEmpty())
	            imgFiles.add(product.getMainImage());
	        if(product.getSubImage1() != null && !product.getSubImage1().isEmpty())
	            imgFiles.add(product.getSubImage1());
	        if(product.getSubImage2() != null && !product.getSubImage2().isEmpty())
	            imgFiles.add(product.getSubImage2());
	        if(product.getSubImage3() != null && !product.getSubImage3().isEmpty())
	            imgFiles.add(product.getSubImage3());
	        if(product.getSubImage4() != null && !product.getSubImage4().isEmpty())
	            imgFiles.add(product.getSubImage4());

	        model.addAttribute("imgNum", imgFiles); // imgNum에 이미지 파일명 리스트 담기
	    }
		
		// 현재 상품 제외한 해당 기업의 다른 상품들 (3개 제한)
	    List<Map<String, Object>> allProducts = planingService.getProductsWithImages(comNum);

	    List<Map<String, Object>> filteredProducts = allProducts.stream()
	        .filter(p -> !String.valueOf(p.get("PRD_NUM")).equals(productId)) // 현재 상품 제외
	        .limit(3)
	        .collect(Collectors.toList());

	    model.addAttribute("products", filteredProducts);
	    
	 // 같은 기업의 다른 랜덤 상품들 (현재 상품 제외)
	    List<Map<String, Object>> relatedProducts = planingService.getRandomProductsWithImage(comNum, productId);
	    model.addAttribute("relatedProducts", relatedProducts);
	    
	    List<ReviewDTO> reviewList = planingService.getReviewList(comNum);
		int reviewCount = planingService.getReviewCount(comNum);

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("reviewCount", reviewCount);
        
        
		
		return "user/product/planingDetail";
	}
	
	@GetMapping("/planingSeller")
	public String planingSeller(@RequestParam("id") String sellerId,
			@RequestParam("comNum") String comNum,
			@Param("sellType") String sellType,
			Model model) {
		// 기업 리스트 조회
		List<CategoryWithProductWithFavoriteWithCompanyWithImageDTO> companieProduct = planingService.getCompanyProduct(sellerId);
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
		
		List<ReviewDTO> reviewList = planingService.getReviewList(comNum);
		int reviewCount = planingService.getReviewCount(comNum);

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("reviewCount", reviewCount);
		
		return "user/product/planingSeller";
	}
	
	// 2. Ajax 요청 처리: 상품 목록을 JSON으로 반환
	@PostMapping("/planingSeller/products/json")
	@ResponseBody
	public Map<String, Object> getProductsJson(@RequestBody Map<String, String> req) {
	    String comNum = req.get("comNum");
	    String sellType = req.get("sellType");
	    String sort = req.get("sort");
	    
	    List<ProductDTO> products;

	    if ("all".equals(sellType)) {
	        products = planingService.AllProductsByCompanySort(comNum, sort);
	    } else {
	        String sellTypeCode = "Y".equals(sellType) ? "Y" : "N";
	        products = planingService.ProductsByCompanyAndSellTypeSort(comNum, sellTypeCode, sort);
	    }

	    List<Map<String, Object>> productList = new ArrayList<>();
	    for (ProductDTO p : products) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("prdNum", p.getPrdNum());
	        map.put("title", p.getTitle());
	        map.put("price", p.getPrice());
	        map.put("location1", p.getLocation1());
	        map.put("inputDate", p.getInputDate().toString()); // LocalDate → 문자열
	        map.put("sellType", p.getSellType());
	        
	        productList.add(map);
	    }

	    Map<String, Object> result = new HashMap<>();
	    result.put("products", productList);
	    return result;
	}
		
}
