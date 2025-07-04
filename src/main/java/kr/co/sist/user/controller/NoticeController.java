package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.user.Service.NoticeService;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/notice")
	public String notice(Model model) {
		List<NoticeDTO> noticeList = noticeService.selectNoticeList();
		model.addAttribute("noticeList", noticeList);
		return "user/notice";
	}
	
	@GetMapping("/event")
	public String event() {
		return "user/event";
	}
	
}
