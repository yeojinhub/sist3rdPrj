package kr.co.sist.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sist.DTO.AdminDTO;
import kr.co.sist.admin.Service.AdminAccountAdminService;

@RequestMapping("/admin")
@Controller
public class AdminAccountAdminController {

	@Autowired
	private AdminAccountAdminService adminService;

	/**
	 * 관리자 계정 관리 페이지로 이동
	 * @param model
	 * @return admin/account/adminList
	 */
	@GetMapping("/account/admins")
	public String adminPage(@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String roleType, Model model) {
		
	    String key = keyword.isBlank() ? null : keyword.trim();
	    String role = roleType.isBlank() ? null : roleType.trim();

	    // 2) Map 생성
	    Map<String, Object> map = new HashMap<>();
	    map.put("keyword", key);
	    map.put("roleType", role); // or cond.put("roleType", rtInt);

	    // 3) 조건 유무 판단 후 조회
	    boolean noCond = (key == null) && (role == null);
	    List<AdminDTO> adminList = noCond
	            ? adminService.searchAllAdmin()
	            : adminService.searchKeyword(map);  // Map 넘김

	    // 4) 모델 바인딩
	    model.addAttribute("adminList", adminList);
	    model.addAttribute("keyword", key);
	    model.addAttribute("roleType", role);
		
		return "admin/account/adminList";
	} //adminPage
	
	/**
	 * 관리자 계정 등록 페이지로 이동
	 * @param model
	 * @return adminAdd
	 */
	@GetMapping("/account/adminAdd")
	public String adminAddPage(Model model) {
		model.addAttribute("today", java.time.LocalDate.now());
		
		return "admin/account/adminAdd";
	} //adminAddPage
	
	/**
	 * 관리자 계정 상세 페이지로 이동
	 * @param admNum 조회할 관리자 번호
	 * @param model
	 * @return admin/account/adminDetail
	 */
	@GetMapping("/account/adminDetail")
	public String adminDetailPage(@RequestParam("admNum") String admNum,
			Model model) {
		
		model.addAttribute("adminDTO", adminService.searchOneAdmin(admNum));
		
		return "admin/account/adminDetail";
	} //adminDetailPage
	
	/**
	 * 관리자 계정 등록
	 * @param adminDTO 등록할 관리자 정보
	 * @return map 성공시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@RequestMapping(value="/account/adminAddProcess", method= { RequestMethod.POST})
	public Map<String, Object> addProcess(@RequestBody AdminDTO adminDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("addProcess DTO 값 "+adminDTO);
		
		try {
	        boolean addFlag = adminService.addAdmin(adminDTO);
	        // 등록 성공
	        map.put("result", addFlag);
	        
	        if (!addFlag) {
	        	// 등록 실패
	            map.put("msg", "관리자 정보 등록 처리에 실패했습니다.");
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
	 * 관리자 계정 수정
	 * @param adminDTO 수정할 관리자 정보
	 * @return map 성공시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@RequestMapping(value="/account/adminModifyProcess", method= { RequestMethod.POST})
	public Map<String, Object> modifyProcess(@RequestBody AdminDTO adminDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("modifyProcess DTO 값 "+adminDTO);
		
		try {
	        boolean modifyFlag = adminService.modifyAdmin(adminDTO);
	        // 수정 성공
	        map.put("result", modifyFlag);
	        
	        if (!modifyFlag) {
	        	// 수정 실패
	            map.put("msg", "관리자 정보 수정 처리에 실패했습니다.");
	        } //end if
		} catch(Exception e) {
			e.printStackTrace();
			
			// 수정 실패
			map.put("result", false);
			map.put("msg", "서버 오류로 인해 수정에 실패했습니다.");
		} //end try catch
		
		return map;
	} //modifyProcess
	
	/**
	 * 관리자 비밀번호 초기화
	 * @param admNum 수정할 관리자 번호
	 * @return map 성공시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@PostMapping("/account/adminPassModifyProcess")
	public Map<String, Object> passModifyProcess(@RequestParam("admNum") String admNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			boolean modifyFlag = adminService.passModifyAdmin(admNum);
			// 초기화 성공
			map.put("result", modifyFlag);
			
			if (!modifyFlag) {
				// 초기화 실패
				map.put("msg", "비밀번호 초기화 처리에 실패했습니다.");
			} //end if
		} catch(Exception e) {
			e.printStackTrace();
			
			// 초기화 실패
			map.put("result", false);
			map.put("msg", "서버 오류로 인해 초기화에 실패했습니다.");
		} //end try catch
		
		return map;
	} //passModifyProcess
	
	
} //class
