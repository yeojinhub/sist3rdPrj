package kr.co.sist.user.Service;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;

/**
 * JWT 관련 기능 정의를 위한 인터페이스
 * 토큰 생성, 검증, 파싱, 인증 정보 추출 등 핵심 메서드를 선언
 */
public interface JwtService {
	
    /**
     * 지정한 키-값, 만료 시간을 기준으로 JWT 토큰을 생성
     * @param Claim의 key
     * @param Clamin의 value
     * @param expireTime 만료 시간
     * @return 생성된 JWT 토큰 문자열
     */
    String getToken(String key, Object value, int expireTime);
    
    /**
     * JWT 토큰으로부터 Claims(내부 정보)를 파싱해 반환
     * @param token JWT 반환
     * @return JWT 내에 포함된 Claims 정보
     */
    Claims getClaims(String token);
    
	/**
	 * 기본 설정에 따라 JWT 토큰을 생성
	 * @param userNum 사용자 번호
	 * @return 생성된 JWT 토큰 문자열
	 */
	String createToken(String userNum);
	
    /**
     * 토큰의 유효성을 검증
     * @param token 검증할 JWT 문자열
     * @return 유효한 토큰이면 true, 아니면 false 반환
     */
    boolean validateToken(String token);
    
    /**
     * 토큰에서 사용자 인증 정보를 추출하여
     * Spring Security의 Authentication 객체로 반환
     * @param token JWT 문자열
     * @return 인증 객체
     */
    Authentication getAuthentication(String token);
    
} //interface
