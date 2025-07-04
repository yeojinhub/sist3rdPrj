package kr.co.sist.user.Service;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;

public interface JwtService {
	
    String getToken(String key, Object value, int expireTime);
    Claims getClaims(String token);
	String createToken(String email);
	
    boolean validateToken(String token);
    Authentication getAuthentication(String token);
    
} //interface
