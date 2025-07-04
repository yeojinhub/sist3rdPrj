package kr.co.sist.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.admin.Service.AdminNoticeService;

@Controller
@RequestMapping("/admin")
public class AdminNoticeController {

	@Autowired
	private AdminNoticeService ans;
	
	@GetMapping("/notice")
	public String noticeList(Model model) {
		
		model.addAttribute("noticeList",ans.searchAllNotice());
		
		return "admin/notice/noticeList";
	}
	
}
