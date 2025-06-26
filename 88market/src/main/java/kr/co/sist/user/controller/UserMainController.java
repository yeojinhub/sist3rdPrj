package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserMainController {

	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@GetMapping("/notice")
	public String notice() {
		return "user/notice";
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "user/account/mypage";
	}
	
	@GetMapping("/detail")
	public String productDetail(@RequestParam("id") int prodcutId) {
		return "user/product/detail";
	}
	
	@GetMapping("/seller")
	public String seller(@RequestParam("id") int sellerId) {
		return "user/product/seller";
	}
	
	@GetMapping("/serviceCenter")
	public String serviceCenter() {
		return "user/serviceCenter";
	}
	
	@GetMapping("/event")
	public String event() {
		return "user/event";
	}
	
	@GetMapping("/search")
	public String search(Model model ,@RequestParam("keyword") String keyword) {
		model.addAttribute("keyword", keyword);
		return "user/search";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/account/login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "user/account/signup";
	}
	
	@GetMapping("/sell")
	public String sell() {
		return "user/product/sell";
	}
	
	@GetMapping("/buy")
	public String buy() {
		return "user/product/buy";
	}
	
	@GetMapping("/anticheat")
	public String antiCheat() {
		return "user/anti_cheat";
	}
	
	@GetMapping("/planingsell")
	public String planingsell() {
		return "user/planingsell";
	}
	
}
