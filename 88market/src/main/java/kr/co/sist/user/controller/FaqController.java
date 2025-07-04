package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.DTO.FaqDTO;
import kr.co.sist.user.Service.FaqService;

@Controller
public class FaqController {

	@Autowired
	private FaqService faqService;
	
	@GetMapping("/serviceCenter")
	public String serviceCenter(
			Model model,
			@RequestParam(value="type", defaultValue="") String type,
			@RequestParam(value="keyword", defaultValue="") String keyword) {
		if (keyword.equals("")) {
			List<FaqDTO> faqList = type.equals("") ? faqService.selectFaqList() : faqService.selectFaqListByType(type);
			model.addAttribute("faqList", faqList);
			model.addAttribute("typeButtonHide", false);
		} else {
			List<FaqDTO> faqList = faqService.selectFaqListByKeyword(keyword);
			model.addAttribute("faqList", faqList);
			model.addAttribute("typeButtonHide", true);
		}
		return "user/serviceCenter";
	}
	
}
