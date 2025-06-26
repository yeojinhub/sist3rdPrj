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
	
    @GetMapping( {"", "/"} )
    public String adminIndex() {
        return "admin/admin_index";
    }

    // 예: 회원 목록 페이지
    /*
    @GetMapping("/account/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "회원 관리 :: 관리자 페이지");
        model.addAttribute("contentFragment", "account/user_list :: userList");
        return "layout";
    }
    */
	
} //class
