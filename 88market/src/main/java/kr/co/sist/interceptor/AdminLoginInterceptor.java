package kr.co.sist.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않음

        // 로그인 페이지는 인터셉터에서 제외
        if (request.getRequestURI().equals(request.getContextPath() + "/admin/login")) {
            return true;
        }

        // 세션이 없거나, 로그인된 관리자/기업 정보가 없으면 로그인 페이지로 리다이렉트
        if (session == null || (session.getAttribute("loggedInAdmin") == null && session.getAttribute("loggedInCompany") == null)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false; // 요청 처리 중단
        }

        return true; // 요청 계속 진행
    }
}
