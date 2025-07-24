package kr.co.sist.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CompanyHomeController {

	@GetMapping("/companyHome")
	public String companyHome(Model model) {
		
		model.addAttribute("pageTitle","기획관 홈");
		
		return "company/company_index";
	} //companyHome
}
