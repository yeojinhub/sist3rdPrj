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
            // CSRF 비활성화 및 세션 상태를 Stateless로 설정
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // 요청 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 인증이 필요한 경로
                .requestMatchers(
                    "/chat",
                    "/buy",
                    "/sell",
                    "/product/**",
                    "/inquiry"
                ).authenticated()
                // 그 외(메인, 로그인, 회원가입, 정적 리소스 등)는 모두 허용
                .anyRequest().permitAll()
            )
            // 예외 처리
            .exceptionHandling(ex -> ex
                // 인증이 안 된 경우(토큰 없거나 잘못된 경우) → 401 또는 로그인 페이지로 리다이렉트
                .authenticationEntryPoint((request, response, authException) -> {
                    String acceptHeader = request.getHeader("Accept");
                    if (acceptHeader != null && acceptHeader.contains("application/json")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\":\"인증이 필요합니다\"}");
                    } else {
                        response.sendRedirect("http://localhost:8080/login");
                    }
                })
                // 인증은 됐지만 권한이 없는 경우 → 로그인 페이지로 리다이렉트
                .accessDeniedHandler((request, response, accessDeniedException) ->
                    response.sendRedirect("http://localhost:8080/login")
                )
            )
            // JwtFilter를 UsernamePasswordAuthenticationFilter 전에 등록
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

