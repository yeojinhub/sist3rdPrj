package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@GetMapping("/account/login_email")
	public String login(Model model) {
		List<UserDTO> loginList = service.selectLoginList();
		model.addAttribute("loginList", loginList);
		
		return "user/account/login_email";
	} //login

} //class
