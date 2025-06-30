package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.dto.FaqDTO;
import kr.co.sist.dto.NoticeDTO;
import kr.co.sist.user.Service.FaqService;
import kr.co.sist.user.Service.NoticeService;

@Controller
public class UserMainController {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FaqService faqService;

	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@GetMapping("/notice")
	public String notice(Model model) {
		List<NoticeDTO> noticeList = noticeService.selectNoticeList();
		model.addAttribute("noticeList", noticeList);
		return "user/notice";
	}
	
	@GetMapping("/mypage")
	public String mypage(HttpSession session) {
	    // 세션에 로그인된 유저 정보가 없으면 로그인 페이지로 리다이렉트
	    if (session.getAttribute("user") == null) {
	        return "redirect:/login";
	    }
	    // 로그인 되어 있으면 마이페이지로 이동
	    return "user/account/mypage";
	}
	
	@GetMapping("/detail")
	public String productDetail(@RequestParam("id") int prodcutId) {
		return "user/product/detail";
	}
	
	@GetMapping("/seller")
	public String seller(@RequestParam("id") int sellerId) {
		return "user/product/seller";
	}
	
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
	
	@GetMapping("/event")
	public String event() {
		return "user/event";
	}
	
	@GetMapping("/search")
	public String search(Model model ,@RequestParam("keyword") String keyword) {
		model.addAttribute("keyword", keyword);
		return "user/search";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/account/login";
	}
	
	@PostMapping("/login")
	public String doLogin(@RequestParam("loginType") String loginType, HttpSession session) {
		// 하드코딩된 유저 식별자 저장
		switch (loginType) {
			case "naver":
				session.setAttribute("user", "user01");
				break;
			case "kakao":
				session.setAttribute("user", "user02");
				break;
			case "email":
				session.setAttribute("user", "user03");
				break;
		}
		return "redirect:/"; // 로그인 후 메인페이지로 이동
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); // 세션 완전 초기화 (로그아웃)
	    return "redirect:/";  // 홈으로 이동
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "user/account/signup";
	}
	
	@GetMapping("/sell")
	public String sell() {
		return "user/product/sell";
	}
	
	@GetMapping("/buy")
	public String buy() {
		return "user/product/buy";
	}
	
	@GetMapping("/anticheat")
	public String antiCheat() {
		return "user/anti_cheat";
	}
	
	@GetMapping("/planingsell")
	public String planingsell() {
		return "user/planingsell";
	}
	
}
