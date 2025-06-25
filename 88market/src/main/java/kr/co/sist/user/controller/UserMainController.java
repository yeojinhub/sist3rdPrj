package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
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
	public String marketDetail(@RequestParam("id") int prodcutId) {
		return "user/product/detail";
	}
	
	@GetMapping("/serviceCenter")
	public String marketFaq() {
		return "user/serviceCenter";
	}
	
	@GetMapping("/event")
	public String marketEvent() {
		return "user/event";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/account/login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "user/account/signup";
	}
	
}
