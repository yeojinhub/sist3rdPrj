package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

	@GetMapping("/search")
	public String search(Model model, @RequestParam("keyword") String keyword) {
		model.addAttribute("keyword", keyword);
		return "user/search";
	}
	
}
