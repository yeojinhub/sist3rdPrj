package kr.co.sist.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sist.DTO.InquiryDTO;

@Controller
public class InquiryController {

	@GetMapping("/inquiry")
	@ResponseBody
	public ResponseEntity<String> inquiry(HttpServletRequest request) {
		return ResponseEntity.ok("로그인 체크 완료");
	}// inquiry

	@PostMapping("/inquiry/add")
	@ResponseBody
	public ResponseEntity<String> inquiryAdd(
		MultipartFile[] files,
		InquiryDTO inquiryDTO
		) {

			System.out.println(inquiryDTO);
			for (MultipartFile file : files) {
				System.out.println(file.getOriginalFilename());
			}

		return ResponseEntity.ok("문의 등록 완료");
	}// inquiryAdd
	
}// class
