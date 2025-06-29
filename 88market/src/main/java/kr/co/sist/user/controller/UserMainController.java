package kr.co.sist.user.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String mypage() {
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
