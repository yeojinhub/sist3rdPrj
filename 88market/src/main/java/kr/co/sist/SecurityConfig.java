package kr.co.sist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.sist.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    	.csrf(csrf -> csrf.disable())
    	.sessionManagement(sm -> sm
    			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    			)
    	// 요청 권한 설정
    	.authorizeHttpRequests(auth -> auth
    			// 나머지는 인증 필요
    			.requestMatchers(
    					"/chat",
    					"/buy",
    					"/sell",
    					"/product/**",
    					"/inquiry"
    					).authenticated()
    			// 로그인 페이지·API와 정적 리소스는 누구나
    			.anyRequest().permitAll()
    			)
    	.exceptionHandling(ex -> ex
    			// 인증이 안 된 경우(토큰이 없거나 잘못된 경우) → 401
    			.authenticationEntryPoint((request, response, authException) -> {
    				String acceptHeader = request.getHeader("Accept");
    		        
    		        // 비동기 요청 (JSON 응답 원하는 경우)
    		        if (acceptHeader != null && acceptHeader.contains("application/json")) {
    		            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		            response.setContentType("application/json");
    		            response.getWriter().write("{\"error\":\"인증이 필요합니다\"}");
    		        } else {
    		            // 일반 브라우저 요청
    		            response.sendRedirect("http://localhost:8080/login");
    		        }
    			})
    			// 인증은 됐지만 권한이 없는 경우 → 403
    			.accessDeniedHandler((request, response, accessDeniedException) ->
    			response.sendRedirect("http://localhost:8080/login"))
    			)
    	.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
