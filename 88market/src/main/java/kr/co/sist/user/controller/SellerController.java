package kr.co.sist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.sist.user.Service.SellerService;

@Controller
public class SellerController {

	@Autowired
	private SellerService ss;
	
    @GetMapping("/seller/{id}")
    public String seller(@PathVariable("id") String userNum, Model model) {
    	
    	model.addAttribute("users", ss.searchUserInfoByUserNum(userNum)); // 유저 정보
    	
    	
        return "user/product/seller";
    }// seller
	
}// class
