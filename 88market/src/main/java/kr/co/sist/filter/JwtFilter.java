package kr.co.sist.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.sist.user.Service.JwtService;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    String path = request.getRequestURI();
	    // 이 경로들은 JwtFilter.doFilterInternal()을 아예 실행하지 않는다
	    return path.equals("/")
	        || path.equals("/login")
	        || path.equals("/signup")
	        || path.startsWith("/css/")
	        || path.startsWith("/js/")
	        || path.startsWith("/images/");
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String token = null;
		
		// 쿠키에서 token 꺼내기
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			for(Cookie c : cookies) {
				if("token".equals(c.getName())) {
					token = c.getValue();
					break;
				} //end if
			} //end for
		} //end if
		
        if (token != null && !token.isEmpty()) {
        	try {
        		if(jwtService.validateToken(token)) {
        			Authentication auth = jwtService.getAuthentication(token);
        			SecurityContextHolder.getContext().setAuthentication(auth);
        		} //end if
        	} catch(Exception e) {
        		// 토큰 예외 발생 시 컨텍스트 초기화
        		SecurityContextHolder.clearContext();
        	} //end try catch
        } //end if
        
        // 다음 필터로 진행
        chain.doFilter(request, response);
        
	} //doFilterInternal
	
} //class
