package kr.co.sist.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sist.DTO.AnswerDTO;
import kr.co.sist.DTO.InquiryDTO;
import kr.co.sist.admin.Service.AdminInquiryService;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Controller
@RequestMapping("/admin")
public class AdminInquiryController {
    
    @Autowired
    private AdminInquiryService adminInquiryService;

    @GetMapping("/inquiry")
	public String inquiry(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            Model model) {
		
        Pagination pagination = new Pagination();
        pagination.setPageNum(page);

        // 검색 조건 객체 생성
        SearchDTO sDTO = new SearchDTO();
        sDTO.setSearchType(searchType);
        sDTO.setKeyword(keyword);

        // 서비스 호출 (검색 조건 추가)
        model.addAttribute("inquiryList", adminInquiryService.getAllInquiry(pagination, sDTO));
        model.addAttribute("pagination", pagination);

        // 검색 조건 유지를 위해 모델에 추가
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
		
		return "admin/inquiry/inquiryList";
	}// inquiry

    @GetMapping("/inquiry/answer/{inqNum}")
    public String inquiryAnswer(@PathVariable int inqNum, Model model) {

        // 1. inqNum에 해당하는 문의 내용 조회 및 모델에 추가.
        model.addAttribute("inquiry", adminInquiryService.getInquiryByInqNum(inqNum));

        // 2. 임시로 어드민넘버 가데이터 모델에 추가
        model.addAttribute("nowAdminId", 1);

        return "admin/inquiry/inquiryAnswer";
    }// inquiryAnswer

    @PostMapping("/inquiry/answer/add")
    @ResponseBody
    public ResponseEntity<String> addAnswer(@RequestBody AnswerDTO answerDTO) {
        adminInquiryService.addAnswer(answerDTO);
        return ResponseEntity.ok("success");
    }// addAnswer

    @PostMapping("/inquiry/answer/modify")
    @ResponseBody
    public ResponseEntity<String> modifyAnswer(@RequestBody AnswerDTO answerDTO) {
        adminInquiryService.modifyAnswer(answerDTO);
        return ResponseEntity.ok("success");
    }// modifyAnswer

    @PostMapping("/inquiry/answer/delete")
    @ResponseBody
    public ResponseEntity<String> deleteAnswer(@RequestBody AnswerDTO answerDTO) {
        adminInquiryService.deleteInquiry(answerDTO.getInqNum());
        return ResponseEntity.ok("success");
    }// deleteAnswer

}// class
