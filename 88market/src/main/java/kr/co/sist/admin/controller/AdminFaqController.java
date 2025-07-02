package kr.co.sist.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sist.DTO.FaqDTO;
import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.admin.DAO.AdminFaqDAO;
import kr.co.sist.admin.Service.AdminFaqService;
import kr.co.sist.admin.util.FaqSearchDTO;
import kr.co.sist.admin.util.Pagination;

@Controller
@RequestMapping("/admin")
public class AdminFaqController {

	@Autowired
	private AdminFaqService afs;
	
	@GetMapping("/faq")
	public String faq(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            Model model) {
		
        Pagination pagination = new Pagination();
        pagination.setPageNum(page);

        // 검색 조건 객체 생성
        FaqSearchDTO fsDTO = new FaqSearchDTO();
        fsDTO.setSearchType(searchType);
        fsDTO.setKeyword(keyword);

        // 서비스 호출 (검색 조건 추가)
        model.addAttribute("faqList", afs.getFaqList(pagination, fsDTO));
        model.addAttribute("pagination", pagination);

        // 검색 조건 유지를 위해 모델에 추가
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
		
		
		return "admin/faq/faqList";
	}
	
	@PostMapping("/faq/delete")
    @ResponseBody
    public ResponseEntity<String> deletefaqs(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> faqNums = request.get("faqNums");
            afs.deleteFaqs(faqNums);
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("삭제 실패");
        }
    }
	
    @GetMapping("/faq/add")
    public String faqAdd() {
        return "admin/faq/faqAdd";
    }
    
    @PostMapping("/faq/add")
    public String faqAdd(FaqDTO faqDTO) {
        afs.insertFaq(faqDTO);
        return "redirect:/admin/faq";
    }
    
    @GetMapping("/faq/detail")
    public String faqDetail(
            @RequestParam("faqNum") int faqNum,
            Model model) {
        FaqDTO faqDTO = afs.getFaqDetail(faqNum);
        model.addAttribute("faqDTO", faqDTO);
        model.addAttribute("faqNum", faqNum);
        return "admin/faq/faqDetail";
    }

    @PostMapping("/faq/modify")
    public String faqModify(FaqDTO faqDTO) {
        afs.updateFaq(faqDTO);
        return "redirect:/admin/faq";
    }
	
}
