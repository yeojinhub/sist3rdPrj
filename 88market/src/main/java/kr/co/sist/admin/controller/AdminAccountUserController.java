package kr.co.sist.admin.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.admin.Service.AdminAccountUserService;

@RequestMapping("/admin")
@Controller
public class AdminAccountUserController {

	@Autowired
	private AdminAccountUserService userService;

	/**
	 * 사용자 계정관리 페이지로 이동
	 * @param model
	 * @return admin/account/userList
	 */
	@GetMapping("/account/users")
	public String userPage(Model model) {
		model.addAttribute("userList", userService.searchAllUser());
		
		return "admin/account/userList";
	} //userPage
	
	/**
	 * 사용자 계정 등록 페이지로 이동
	 * @param model
	 * @return admin/account/userAdd
	 */
	@GetMapping("/account/userAdd")
	public String userAddPage(Model model) {
		model.addAttribute("today", java.time.LocalDate.now());
		
		return "admin/account/userAdd";
	} //userAddPage
	
	/**
	 * 사용자 계정 상세 페이지로 이동
	 * @param userNum 조회할 회원 번호
	 * @param tab 3개의 탭으로 구성(상세정보, 계좌 조회, 배송지 조회)
	 * @param model
	 * @return admin/account/userDetail
	 */
	@GetMapping("/account/userDetail")
	public String userDetailPage(@RequestParam("userNum") String userNum,
			 @RequestParam(value="tab", defaultValue="personal") String tab,
			 Model model) {
		
		model.addAttribute("userDTO", userService.searchOneUser(userNum));
		model.addAttribute("bankList", userService.searchAllBank(userNum));
		model.addAttribute("addressList", userService.searchAllAddress(userNum));
		model.addAttribute("tab", tab);
		
		return "admin/account/userDetail";
	} //userDetailPage
	
	/**
	 * 회원 정보 등록
	 * @param userDTO 등록할 회원 정보
	 * @return map 성공 시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@RequestMapping(value="/account/userAddProcess", method= { RequestMethod.POST})
	public Map<String, Object> addProcess(@RequestBody UserDTO userDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("addProcess DTO 값 "+userDTO);
		
		try {
	        boolean addFlag = userService.addUser(userDTO);
	        // 등록 성공
	        map.put("result", addFlag);
	        
	        if (!addFlag) {
	        	// 등록 실패
	            map.put("msg", "회원 정보 등록 처리에 실패했습니다.");
	        } //end if
		} catch(Exception e) {
			e.printStackTrace();
			// 등록 실패
			map.put("result", false);
			map.put("msg", "서버 오류로 인해 등록에 실패했습니다.");
		} //end try catch
		
		return map;
	} //addProcess
	
	/**
	 * 회원 정보 수정
	 * @param userDTO 수정할 회원 정보
	 * @return map 성공 시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@RequestMapping(value="/account/userModifyProcess", method= { RequestMethod.POST})
	public Map<String, Object> modifyProcess(@RequestBody UserDTO userDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("modifyProcess DTO 값 "+userDTO);
		
		try {
	        boolean modifyFlag = userService.modifyUser(userDTO);
	        // 수정 성공
	        map.put("result", modifyFlag);
	        
	        if (!modifyFlag) {
	        	// 수정 실패
	            map.put("msg", "회원 정보 수정 처리에 실패했습니다.");
	        } //end if
		} catch(Exception e) {
			e.printStackTrace();
			
			// 수정 실패
			map.put("result", false);
			map.put("msg", "서버 오류로 인해 수정에 실패했습니다.");
		} //end try catch
		
		return map;
	} //modifyProcess
	
} //class
