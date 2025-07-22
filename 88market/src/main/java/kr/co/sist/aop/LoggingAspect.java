package kr.co.sist.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    
    private static final Logger apiLogger = LoggerFactory.getLogger("kr.co.sist.ApiLogger");
    
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object logApiCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String params = getRequestParams(request);
        
        // REQUEST 로그
        apiLogger.info("REQUEST / {} {} {}", method, uri, params);
        
        Object result = joinPoint.proceed();
        
        // RESPONSE 로그  
        apiLogger.info("RESPONSE / {} {} {}", method, uri, params);
        
        return result;
    }
    
    private String getRequestParams(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap.isEmpty()) return "{}";
        
        StringBuilder sb = new StringBuilder("{");
        paramMap.forEach((key, values) -> 
            sb.append(key).append("=").append(String.join(",", values)).append(", "));
        
        return sb.length() > 1 ? sb.substring(0, sb.length()-2) + "}" : "{}";
    }
    
}