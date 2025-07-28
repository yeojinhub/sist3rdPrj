package kr.co.sist.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import kr.co.sist.DTO.AdminDTO;
import kr.co.sist.DTO.CompanyDTO;
import kr.co.sist.admin.Service.AdminLoginService;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    /**
     * 관리자 로그인 폼을 보여줍니다.
     * @param model Model 객체
     * @param session HttpSession 객체
     * @return 로그인 폼 뷰 이름
     */
    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        // 이미 로그인된 상태라면 홈으로 리다이렉트
        if (session.getAttribute("loggedInAdmin") != null || session.getAttribute("loggedInCompany") != null) {
            if (session.getAttribute("loggedInAdmin") != null) {
                return "redirect:/admin/home";
            } else {
                return "redirect:/admin/planing/planingList";
            }
        }
        model.addAttribute("pageTitle", "관리자 로그인");
        return "admin/login";
    }

    /**
     * 관리자 또는 기업 계정으로 로그인 처리를 합니다.
     * @param accountType 계정 타입 (ADMIN 또는 COMPANY)
     * @param id 사용자 ID
     * @param pass 비밀번호
     * @param model Model 객체
     * @param session HttpSession 객체
     * @return 로그인 성공 시 해당 대시보드로 리다이렉트, 실패 시 로그인 폼으로 에러 메시지와 함께 리다이렉트
     */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam("accountType") String accountType,
            @RequestParam("id") String id,
            @RequestParam("pass") String pass,
            Model model,
            HttpSession session) {

        if ("ADMIN".equals(accountType)) {
            AdminDTO admin = adminLoginService.loginAdmin(id, pass);
            if (admin != null) {
                session.setAttribute("loggedInAccountType", "ADMIN");
                session.setAttribute("loggedInAdmin", admin);
                session.setAttribute("loggedInName", admin.getName()); // 헤더에 표시할 이름
                session.setAttribute("loginId", admin.getId());
                session.setAttribute("rollType", admin.getRollType()); // 또는 admin.getAdminType(), admin.getRoleCode()
                return "redirect:/admin/home"; // 관리자 홈으로 리다이렉트
            } else {
                model.addAttribute("error", "관리자 아이디 또는 비밀번호가 올바르지 않습니다.");
            }
        } else if ("COMPANY".equals(accountType)) {
            CompanyDTO company = adminLoginService.loginCompany(id, pass);
            if (company != null) {
                session.setAttribute("loggedInAccountType", "COMPANY");
                session.setAttribute("loggedInCompany", company);
                session.setAttribute("loggedInName", company.getComName()); // 헤더에 표시할 기업 이름
                session.setAttribute("loginId", company.getId());
                return "redirect:/admin/planing/planingList"; // 기업 상품 관리 페이지로 리다이렉트
            } else {
                model.addAttribute("error", "기업 아이디 또는 비밀번호가 올바르지 않습니다.");
            }
        } else {
            model.addAttribute("error", "유효하지 않은 계정 타입입니다.");
        }

        model.addAttribute("id", id); // 입력했던 ID 유지
        return "admin/login"; // 로그인 실패 시 다시 로그인 폼으로
    }

    /**
     * 로그아웃 처리를 합니다.
     * @param session HttpSession 객체
     * @return 로그인 폼으로 리다이렉트
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/admin/login";
    }
}
