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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.sist.DTO.ReportDTO;
import kr.co.sist.admin.Service.AdminReportService;
import kr.co.sist.admin.util.SearchDTO;
import kr.co.sist.admin.util.Pagination;

@Controller
@RequestMapping("/admin")
public class AdminReportController {

    @Autowired
    private AdminReportService ars;

    @GetMapping("/report")
    public String reportList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            Model model) {

        // 페이지네이션 객체 생성
        Pagination pagination = new Pagination();
        pagination.setPageNum(page);

        // 검색 조건 객체 생성
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchType(searchType);
        searchDTO.setKeyword(keyword);

        // 서비스 호출: 신고 목록과 페이지네이션 정보 가져오기
        List<ReportDTO> reportList = ars.getReportList(pagination, searchDTO);

        // 모델에 데이터 추가
        model.addAttribute("reportList", reportList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "admin/report/reportList";
    }

    @GetMapping("/report/detail")
    public String reportDetail(@RequestParam("repNum") int repNum, Model model) {
        ReportDTO report = ars.getReportByNum(repNum);
        model.addAttribute("report", report);
        return "admin/report/reportDetail"; // Thymeleaf 경로
    }
    
    @PostMapping("/report/deleteOne")
    public String deleteReport(@RequestParam("repNum") int repNum, RedirectAttributes redirectAttributes) {
        ars.deleteReportByNum(repNum);

        return "redirect:/admin/report";
    }
    
    @PostMapping("/report/delete")
    @ResponseBody
    public ResponseEntity<String> deleteReports(@RequestBody Map<String, List<Integer>> body) {
        List<Integer> repNums = body.get("repNums");
        if (repNums == null || repNums.isEmpty()) {
            return ResponseEntity.badRequest().body("신고 번호가 전달되지 않았습니다.");
        }

        int successCount = 0;
        for (int repNum : repNums) {
            ars.deleteReportByNum(repNum); // 기존 메서드 사용
            successCount++;
        }

        return ResponseEntity.ok(successCount + "건 삭제 완료");
    }

    
}

