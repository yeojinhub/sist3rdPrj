package kr.co.sist.user.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.co.sist.filter.CustomUserDetailsService;

/**
 * JwtService 인터페이스의 구현 클래스
 * JWT 토큰의 생성, 파싱, 검증, 인증객체 반환 로직을 구현
 */
@Service
public class JwtServiceImpl implements JwtService {

	// application.properties에 정의된 시크릿 키
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 사용자 번호로 기본 JWT 토큰 생성
     * 만료 시간은 1시간(1000 * 60 * 60ms)
     */
    @Override
    public String createToken(String userNum) {
        return getToken("userNum", userNum, 1000 * 60 * 60);
    } //createToken

    /**
     * 토큰에서 사용자 정보(Claims)를 추출한 뒤,
     * Spring Security의 Authentication 객체로 변환하여 반환
     */
    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        } //end if
        
        // 토큰에서 사용자 번호 추출
        String usernum = claims.get("userNum", String.class);
        
        // 해당 사용자에 대한 인증 정보 로드
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernum);

        // 인증 객체 생성
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null, // credentials (여기선 이미 인증된 상태이므로 null)
                userDetails.getAuthorities());
    }

    /**
     * 키-값 페어와 만료시간을 이용해 JWT 토큰을 생성
     * HS256 알고리즘 사용
     * 토큰의 Claim에 키-값 포함
     * 유효 시간 설정
     */
    @Override
    public String getToken(String key, Object value, int expireTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(key, value);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())	// 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    } //getToken

    /**
     * JWT 토큰을 파싱하여 Claims 반환
     * 서명 검증 포함
     */
    @Override
    public Claims getClaims(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
            return Jwts.parser()
                    .verifyWith(key) // setSigningKey 대신
                    .build()
                    .parseClaimsJws(token)
                    .getPayload(); // getBody() 대신
        } catch (JwtException e) {
            return null;
        } //end try catch
    } //getClaims

    /**
     * 토큰의 유효성 검사
     */
    public boolean validateToken(String token) {
        return getClaims(token) != null;
    } //validateToken
    
} //class