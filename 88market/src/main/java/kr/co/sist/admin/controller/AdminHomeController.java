package kr.co.sist.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

	@GetMapping("/home")
	public String adminHome(Model model) {
		
		model.addAttribute("pageTitle","관리자 홈");
		
		return "admin/admin_index";
	}
}
