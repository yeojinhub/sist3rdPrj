package kr.co.sist.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.admin.Service.AdminAccountUserService;

@RequestMapping("/admin")
@Controller
public class AdminAccountUserController {

	@Autowired
	private AdminAccountUserService userService;
	
	/**
	 * 사용자 계정관리 페이지로 이동
	 * @return "admin/account/userList"
	 */
	@GetMapping("/account/users")
	public String userPage(Model model) {
		model.addAttribute("userList", userService.searchAllUser());
		
		System.out.println("UserAccountController : List 값 "+userService.searchAllUser());
		
		
		return "admin/account/userList";
	} //userPage
	
	@GetMapping("/account/detail")
	public String userDetailPage(@RequestParam("userNum") String userNum,
			 @RequestParam(value="tab", defaultValue="personal") String tab,
			 Model model) {
		
		model.addAttribute("userDTO", userService.searchOneUser(userNum));
		model.addAttribute("tab", tab);
		
		return "admin/account/userDetail";
	} //userDetailPage
	
} //class
