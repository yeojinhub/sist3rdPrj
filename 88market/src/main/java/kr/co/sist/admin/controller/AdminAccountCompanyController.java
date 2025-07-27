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

import kr.co.sist.DTO.CompanyDTO;
import kr.co.sist.admin.Service.AdminAccountCompanyService;

@RequestMapping("/admin")
@Controller
public class AdminAccountCompanyController {

	@Autowired
	private AdminAccountCompanyService comService;
	
	/**
	 * 기업 계정 관리 페이지로 이동,
	 * 키워드, 계정유형에 따른 기업 전체 조회
	 * @param keyword 검색할 키워드
	 * @param roleType 검색할 계정타입
	 * @param model
	 * @return admin/account/companyList
	 */
	@GetMapping("/account/company")
	public String companyPage(@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String withdraw, Model model) {
		
	    String key = keyword.isBlank() ? null : keyword.trim();
	    String type = withdraw.isBlank() ? null : withdraw.trim();

	    // 2) Map 생성
	    Map<String, Object> map = new HashMap<>();
	    map.put("keyword", key);
	    map.put("withdraw", type);
	    
	    // 3) 조건 유무 판단 후 조회
	    boolean noCond = (key == null) && (type == null);
	    List<CompanyDTO> companyList = noCond
	            ? comService.searchAllCompany()
	            : comService.searchKeyword(map);  // Map 넘김

	    // 4) 모델 바인딩
	    model.addAttribute("companyList", companyList);
	    model.addAttribute("keyword", key);
	    model.addAttribute("withdraw", type);
		
		return "admin/account/companyList";
	} //companyPage
	
	/**
	 * 기업 계정 등록 페이지로 이동
	 * @param model
	 * @return admin/account/companyAdd
	 */
	@GetMapping("/account/companyAdd")
	public String companyAddPage(Model model) {
		model.addAttribute("today", java.time.LocalDate.now());
		
		return "admin/account/companyAdd";
	} //companyAddPage
	
	/**
	 * 기업 계정 상세 페이지로 이동
	 * @param comNum 조회할 기업 번호
	 * @param model
	 * @return admin/account/companyDetail
	 */
	@GetMapping("/account/companyDetail")
	public String companyDetailPage(@RequestParam("comNum") String comNum,
			Model model) {
		
		model.addAttribute("companyDTO", comService.searchOneCompany(comNum));
		
		return "admin/account/companyDetail";
	} //companyDetailPage
	
	/**
	 * 기업 계정 등록
	 * @param companyDTO 등록할 기업 정보
	 * @return map 성공시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@RequestMapping(value="/account/companyAddProcess", method= { RequestMethod.POST})
	public Map<String, Object> addProcess(@RequestBody CompanyDTO companyDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("addProcess DTO 값 "+companyDTO);
		
		try {
	        boolean addFlag = comService.addCompany(companyDTO);
	        // 등록 성공
	        map.put("result", addFlag);
	        
	        if (!addFlag) {
	        	// 등록 실패
	            map.put("msg", "기업 등록 처리에 실패했습니다.");
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
	 * 기업 계정 수정
	 * @param companyDTO 수정할 기업 정보
	 * @return map 성공시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@RequestMapping(value="/account/companyModifyProcess", method= { RequestMethod.POST})
	public Map<String, Object> modifyProcess(@RequestBody CompanyDTO companyDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("modifyProcess DTO 값 "+companyDTO);
		
		try {
	        boolean modifyFlag = comService.modifyCompany(companyDTO);
	        // 수정 성공
	        map.put("result", modifyFlag);
	        
	        if (!modifyFlag) {
	        	// 수정 실패
	            map.put("msg", "기업 수정 처리에 실패했습니다.");
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
	 * 기업 비밀번호 초기화
	 * @param comNum 수정할 기업 번호
	 * @return map 성공시 true, 실패 시 false 반환
	 */
	@ResponseBody
	@PostMapping("/account/companyPassModifyProcess")
	public Map<String, Object> passModifyProcess(@RequestParam("comNum") String comNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			boolean modifyFlag = comService.passModifyCompany(comNum);
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
