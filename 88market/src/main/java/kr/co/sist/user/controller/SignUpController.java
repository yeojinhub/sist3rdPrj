package kr.co.sist.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.SignUpService;

@Controller
public class SignUpController {

	@Autowired
	private SignUpService service;
	
	@ResponseBody
	@RequestMapping(value="/signUpProcess", method = { RequestMethod.POST })
	public Map<String, Object> signUpProcess(@RequestBody UserDTO userDTO) {
		Map<String, Object> map = new HashMap<>();
		
		System.out.println("SignUpController : DTO 값 "+userDTO);
		
		try {
			boolean addFlag = service.addMember(userDTO);
			
			// 회원가입 실패
			if(!addFlag) {
				map.put("success", false);
				map.put("message", "회원가입 처리에 실패했습니다.");
			} else {
				// 회원가입 성공
				map.put("success", true);
			} //end if else
		} catch(Exception e) {
			e.printStackTrace();
			
			// 회원가입 실패
			map.put("success", false);
			map.put("message", "서버 오류로 인해 회원가입이 실패했습니다.");
		} //end try catch
		
		System.out.println("SignUpController : map 값 "+map.toString());
		return map;
	} //signUpProcess;
	
} //class
