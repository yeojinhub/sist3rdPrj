package kr.co.sist.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class InquiryController {

	@GetMapping("/inquiry")
	@ResponseBody
	public ResponseEntity<String> inquiry(HttpServletRequest request) {
		
		return ResponseEntity.ok("야호");
	}// inquiry
	
}// class
