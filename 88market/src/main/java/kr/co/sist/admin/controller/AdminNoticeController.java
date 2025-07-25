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

import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.admin.Service.AdminNoticeService;
import kr.co.sist.admin.util.SearchDTO;
import kr.co.sist.admin.util.Pagination;

@Controller
@RequestMapping("/admin")
public class AdminNoticeController {

    @Autowired
    private AdminNoticeService ans;

    @GetMapping("/notice")
    public String noticeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            Model model) {
        Pagination pagination = new Pagination();
        pagination.setPageNum(page);

        // 검색 조건 객체 생성
        SearchDTO nsDTO = new SearchDTO();
        nsDTO.setSearchType(searchType);
        nsDTO.setKeyword(keyword);

        // 서비스 호출 (검색 조건 추가)
        model.addAttribute("noticeList", ans.getNoticeList(pagination, nsDTO));
        model.addAttribute("pagination", pagination);

        // 검색 조건 유지를 위해 모델에 추가
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "admin/notice/noticeList";
    }

    @GetMapping("/notice/detail")
    public String noticeDetail(
            @RequestParam("notNum") int notNum,
            Model model) {
        NoticeDTO noticeDTO = ans.getNoticeDetail(notNum);
        model.addAttribute("noticeDTO", noticeDTO);
        model.addAttribute("notNum", notNum);
        return "admin/notice/noticeDetail";
    }

    @PostMapping("/notice/modify")
    public String noticeModify(NoticeDTO noticeDTO) {
        ans.updateNotice(noticeDTO);
        return "redirect:/admin/notice";
    }

    @PostMapping("/notice/delete")
    @ResponseBody
    public ResponseEntity<String> deleteNotices(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> notNums = request.get("notNums");
            ans.deleteNotices(notNums);
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("삭제 실패");
        }
    }

    @GetMapping("/notice/add")
    public String noticeAdd() {
        return "admin/notice/noticeAdd";
    }

    @PostMapping("/notice/add")
    public String noticeAdd(NoticeDTO noticeDTO) {
        ans.insertNotice(noticeDTO);
        return "redirect:/admin/notice";
    }
    
    @PostMapping("/notice/hideon")
    @ResponseBody
    public ResponseEntity<String> modifyHideOn(@RequestBody Map<String, List<Integer>> request) {
    	List<Integer> notNums = request.get("notNums");
    	ans.modifyNoticeHideOn(notNums);
    	return ResponseEntity.ok("수정 완료");
    }
    
    @PostMapping("/notice/hideoff")
    @ResponseBody
    public ResponseEntity<String> modifyHideOff(@RequestBody Map<String, List<Integer>> request) {
    	List<Integer> notNums = request.get("notNums");
    	ans.modifyNoticeHideOff(notNums);
    	return ResponseEntity.ok("수정 완료");
    }
}
