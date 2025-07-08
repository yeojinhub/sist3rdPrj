package kr.co.sist.user.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String createToken(String email) {
        return getToken("email", email, 1000 * 60 * 60);
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }

        // 인스턴스를 통해 호출
        String username = claims.get("email", String.class);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // import된 UsernamePasswordAuthenticationToken 사용
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null, // credentials (여기선 이미 인증된 상태이므로 null)
                userDetails.getAuthorities());
    }

    @Override
    public String getToken(String key, Object value, int expireTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(key, value);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

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
        }
    }

    public boolean validateToken(String token) {
        return getClaims(token) != null;
    }
}