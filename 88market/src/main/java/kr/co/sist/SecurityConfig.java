package kr.co.sist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.co.sist.filter.JwtFliter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFliter jwtFilter;

    public SecurityConfig(JwtFliter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authorizeHttpRequests()
            // 로그인 페이지·API와 정적 리소스는 누구나
            .requestMatchers(
               "/login_email", "/login.html",
               "/css/**", "/js/**", "/images/**",
               "/loginProcess"
            ).permitAll()
            // 나머지는 인증 필요
            .anyRequest().authenticated()
          .and()
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}