package kr.co.sist.user.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class NaverService {
    
    @Value("${naver.client-id}")
    private String naverClientId;
    
    @Value("${naver.client-secret}")
    private String naverClientSecret;
    
    @Value("${naver.redirect-uri}")
    private String naverRedirectUri;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    /**
     * 네이버 액세스 토큰 받기
     */
    public String getAccessToken(String code, String state) throws Exception {
    String tokenUrl = "https://nid.naver.com/oauth2.0/token";
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", naverClientId);
    params.add("client_secret", naverClientSecret);
    params.add("code", code);
    params.add("state", state);
    
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    
    try {
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        
        Map<String, Object> responseBody = response.getBody();
        
        // 에러 체크
        if (responseBody.containsKey("error")) {
            throw new Exception("네이버 토큰 발급 실패: " + responseBody.get("error_description"));
        }
        
        String accessToken = (String) responseBody.get("access_token");
        
        if (accessToken == null || accessToken.isEmpty()) {
            throw new Exception("액세스 토큰이 null 또는 비어있음");
        }
        
        return accessToken;
        
    } catch (Exception e) {
        throw e;
    }
}
    
    /**
     * 네이버 사용자 정보 받기
     */
    public Map<String, Object> getUserInfo(String accessToken) throws Exception {
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, Map.class);
        
        return (Map<String, Object>) response.getBody().get("response");
    }
}