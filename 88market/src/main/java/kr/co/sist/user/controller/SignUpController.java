package kr.co.sist.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		map.put("success", service.addMember(userDTO));
		
		System.out.println("SignUpController : map 값 "+map.toString());
		return map;
	} //signUpProcess;
	
} //class
