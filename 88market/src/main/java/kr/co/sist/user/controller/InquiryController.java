package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sist.DTO.InquiryDTO;
import kr.co.sist.user.Service.InquiryService;

@Controller
public class InquiryController {

	@Autowired
	private InquiryService is;
	
	@GetMapping("/inquiry")
	@ResponseBody
	public ResponseEntity<String> inquiry(HttpServletRequest request) {
		return ResponseEntity.ok("로그인 체크 완료");
	}// inquiry

	@PostMapping("/inquiry/add")
	@ResponseBody
	public ResponseEntity<String> inquiryAdd(
		MultipartFile[] files,
		InquiryDTO inquiryDTO,
		@AuthenticationPrincipal UserDetails user
		) {

		try {
			is.addInquiry(inquiryDTO, files, user);
			return ResponseEntity.ok("문의 등록 완료");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("문의 등록 실패");
		}// end try-catch
	}// inquiryAdd

	@GetMapping("/inquiry/list")
	@ResponseBody
	public List<InquiryDTO> inquiryList(
		@AuthenticationPrincipal UserDetails user
		) {
		return is.inquiryList(user);
	}// inquiryList

	@GetMapping("/inquiry/{inqNum}")
	@ResponseBody
	public InquiryDTO inquiryDetail(
		@PathVariable int inqNum
		) {
		return is.inquiryDetail(inqNum);
	}// inquiryDetail
	
}// class
