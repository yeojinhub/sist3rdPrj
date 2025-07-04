package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.DTO.FaqDTO;
import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.user.Service.AnticheatService;
import kr.co.sist.user.Service.FaqService;
import kr.co.sist.user.Service.NoticeService;

@Controller
public class UserMainController {

	@GetMapping("/")
	public String main() {
		return "index";
	}

}
