package kr.co.sist.user.controller;

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
	public String signUpProcess(@RequestBody UserDTO userDTO) {
		
		service.addMember(userDTO);
		
		return "";
	} //signUpProcess;
	
} //class
