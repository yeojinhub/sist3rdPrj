package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

@SessionAttributes({"name","age"})
@Controller
public class LoginController {
	
	@GetMapping("/session_getvalue")
	public String sessionGetValue(HttpSession session) {
		//세션 값 얻기
		String name=(String)session.getAttribute("name");
		Integer sessionAge=(Integer)session.getAttribute("age");
		
		int age=0;
		if(sessionAge != null) {
			age=sessionAge.intValue();
		}
		System.out.println("이름 " + name +" ,  나이 " + age);
		
		return "day0623/session_list";
	}//sessionGetValue
	
	@GetMapping("/model_getvalue")
	public String modelGetValue(Model model) {
		//Spring 5.2 이상에서만 가능하다.
		String name=(String)model.getAttribute("name");

		Integer modelAge=(Integer)model.getAttribute("age");
		
		int age=0;
		if(modelAge != null) {
			age=modelAge.intValue();
		}//end if
		
		System.out.println("Model 이름 " + name +" , Model  나이 " + age);
		
		return "day0623/session_list";
	} //modelGetValue
	
	@GetMapping("/login")
	public String login() {
		return "user/account/login";
	}

	@PostMapping("/login")
	public String doLogin(@RequestParam("loginType") String loginType, HttpSession session) {
		// 하드코딩된 유저 식별자 저장
		switch (loginType) {
			case "naver":
				session.setAttribute("user", "user01");
				break;
			case "kakao":
				session.setAttribute("user", "user02");
				break;
			case "email":
				session.setAttribute("user", "user03");
				break;
		}
		return "redirect:/"; // 로그인 후 메인페이지로 이동
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 완전 초기화 (로그아웃)
		return "redirect:/"; // 홈으로 이동
	}

	@GetMapping("/signup")
	public String signup() {
		return "user/account/signup";
	}

} //class
