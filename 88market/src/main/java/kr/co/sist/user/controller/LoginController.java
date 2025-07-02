package kr.co.sist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@GetMapping("/login_email")
	public String loginEmailPage() {
		return "user/account/login_email";
	} //loginEmailPage
	
	//@GetMapping("/loginProcess")
	@RequestMapping(value="/loginProcess", method= { RequestMethod.POST } )
	public String loginProcess(@RequestParam String email, String pass,
			HttpSession session, Model model) {
		UserDTO loginDTO = service.selectLoginList(email, pass);
		
		if( loginDTO != null ) {
			session.setAttribute("loginDTO", loginDTO);
			System.out.println("로그인 되었습니다.");
			return "redirect:/";
		} else {
			return "redirect:/login";
		} //end if else
		
	} //loginProcess

} //class
