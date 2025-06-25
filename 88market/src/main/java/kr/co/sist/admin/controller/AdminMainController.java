package kr.co.sist.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {

	@GetMapping("/notice")
	public String noticeList() {
		return "admin/notice/noticeList";
	}
	
}
