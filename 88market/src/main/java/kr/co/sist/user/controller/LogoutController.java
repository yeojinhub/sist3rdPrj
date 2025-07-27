package kr.co.sist.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

	@GetMapping("/userLogout")
	@ResponseBody
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
	    // JWT 쿠키 삭제
	    Cookie jwtCookie = new Cookie("token", null);
	    jwtCookie.setMaxAge(0);
	    jwtCookie.setPath("/");
	    jwtCookie.setHttpOnly(true);
	    response.addCookie(jwtCookie);
	    
	    // SecurityContext 정리
	    SecurityContextHolder
	    .clearContext();
	    
	    // 세션 무효화
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    
	    return ResponseEntity.ok(Map.of("success",true));
	}
	
}
