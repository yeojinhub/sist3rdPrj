package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

	@GetMapping("/detail")
	public String productDetail(@RequestParam("id") int prodcutId) {
		return "user/product/detail";
	}
	
	@GetMapping("/sell")
	public String sell() {
		return "user/product/sell";
	}

	@GetMapping("/buy")
	public String buy() {
		return "user/product/buy";
	}
	
	@GetMapping("/seller")
	public String seller(@RequestParam("id") int sellerId) {
		return "user/product/seller";
	}
	
//	@GetMapping("/planingsell")
//	public String planingsell() {
//		return "user/planingsell";
//	}
	
}
