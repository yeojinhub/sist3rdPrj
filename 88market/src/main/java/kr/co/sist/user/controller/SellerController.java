package kr.co.sist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.SellerService;

@Controller
public class SellerController {

	@Autowired
	private SellerService ss;
	
    @GetMapping("/seller/{id}")
    public String seller(@PathVariable("id") String userNum, Model model) {
    	
    	model.addAttribute("users", ss.searchUserInfoByUserNum(userNum)); // 유저 정보
    	model.addAttribute("prdCount",ss.searchUserAllProduct(userNum).size()); // 유저 총 상품 수량
    	
    	int safeCount = 0;
    	for(ProductDTO item : ss.searchUserAllProduct(userNum)) {
    		if (item.getSafeType().equals("Y")) {
    			safeCount++;
    		}// end if
    	}// end for
    	
    	model.addAttribute("safeCount",safeCount); // 안전거래 수량
    	model.addAttribute("productList",ss.searchUserAllProduct(userNum)); // 유저 총 상품 DTO
    	
        return "user/product/seller";
    }// seller
	
}// class
