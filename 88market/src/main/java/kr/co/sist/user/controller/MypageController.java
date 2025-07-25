package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

	@GetMapping("/mypage")
	public String mypage(HttpSession session) {
		// 세션에 로그인된 유저 정보가 없으면 로그인 페이지로 리다이렉트
		if (session.getAttribute("user") == null) {
			return "redirect:/login";
		}
		// 로그인 되어 있으면 마이페이지로 이동
		return "user/account/mypage";
	}
	
}
