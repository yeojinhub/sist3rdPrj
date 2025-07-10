package kr.co.sist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.co.sist.user.Service.SignUpService;

@Controller
public class SignUpController {

	@Autowired
	private SignUpService service;
	
	
} //class
