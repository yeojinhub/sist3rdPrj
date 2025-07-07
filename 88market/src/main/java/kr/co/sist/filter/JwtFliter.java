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
public class JwtFliter extends OncePerRequestFilter {

	private final JwtService jwtService;
	
	public JwtFliter(JwtService jwtService) {
		this.jwtService = jwtService;
	} //JwtFilter
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String token = "";
		
		if(request.getCookies() != null) {
			for(Cookie c : request.getCookies()) {
				if("token".equals(c.getName())) {
					token = c.getValue();
				} //end if
			} //end for
		} //end if
		
        if (token != null && !token.isEmpty() && jwtService.validateToken(token)) {
            Authentication auth = jwtService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } //end if
        chain.doFilter(request, response);
        
	} //doFilterInternal
	
} //class
