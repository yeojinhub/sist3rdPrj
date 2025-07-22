package kr.co.sist.config;

import kr.co.sist.interceptor.AdminLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/**") // /admin으로 시작하는 모든 경로에 적용
                .excludePathPatterns("/admin/login", "/admin/logout", "/css/**", "/js/**", "/images/**"); // 로그인, 로그아웃, 정적 리소스는 제외
    }
}
