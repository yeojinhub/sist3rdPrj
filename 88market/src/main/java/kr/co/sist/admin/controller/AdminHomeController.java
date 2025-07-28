package kr.co.sist.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.sist.DTO.InquiryDTO;
import kr.co.sist.admin.Service.AdminHomeService;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    @Autowired
    private AdminHomeService adminHomeService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/home")
    public String adminHome(Model model) {
        try {
            // 월별 가입자 수 데이터
            List<Map<String, Object>> userRegistrations = adminHomeService.getMonthlyUserRegistrations();
            String userRegistrationsJson = objectMapper.writeValueAsString(userRegistrations);
            model.addAttribute("userRegistrations", userRegistrationsJson);

            // 월별 거래 수 데이터
            List<Map<String, Object>> tradeData = adminHomeService.getMonthlyTradeData();
            String tradeDataJson = objectMapper.writeValueAsString(tradeData);
            model.addAttribute("tradeData", tradeDataJson);

            // 상품 현황 데이터
            model.addAttribute("totalProducts", adminHomeService.getTotalProductCount());
            model.addAttribute("companyProducts", adminHomeService.getCompanyProductCount());
            model.addAttribute("userProducts", adminHomeService.getUserProductCount());

            // 대기 중인 문의사항
            List<InquiryDTO> pendingInquiries = adminHomeService.getPendingInquiries();
            model.addAttribute("pendingInquiries", pendingInquiries);

            System.out.println("=== 관리자 대시보드 데이터 로드 완료 ===");
            System.out.println("가입자 데이터: " + userRegistrations.size() + "개");
            System.out.println("거래 데이터: " + tradeData.size() + "개");
            System.out.println("총 상품: " + adminHomeService.getTotalProductCount());
            System.out.println("문의사항: " + pendingInquiries.size() + "개");

        } catch (JsonProcessingException e) {
            System.err.println("JSON 변환 오류: " + e.getMessage());
            e.printStackTrace();
            // JSON 변환 오류 시 기본값 설정
            model.addAttribute("userRegistrations", "[]");
            model.addAttribute("tradeData", "[]");
            model.addAttribute("totalProducts", 0);
            model.addAttribute("companyProducts", 0);
            model.addAttribute("userProducts", 0);
            model.addAttribute("pendingInquiries", java.util.Collections.emptyList());
        } catch (Exception e) {
            System.err.println("대시보드 데이터 로드 오류: " + e.getMessage());
            e.printStackTrace();
            // 기타 오류 시 기본값 설정
            model.addAttribute("userRegistrations", "[]");
            model.addAttribute("tradeData", "[]");
            model.addAttribute("totalProducts", 0);
            model.addAttribute("companyProducts", 0);
            model.addAttribute("userProducts", 0);
            model.addAttribute("pendingInquiries", java.util.Collections.emptyList());
        }

        return "admin/admin_index";
    }

    @GetMapping("/")
    public String adminIndex(Model model) {
        return adminHome(model);
    }
}
