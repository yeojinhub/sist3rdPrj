package kr.co.sist.user.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.JwtService;
import kr.co.sist.user.Service.KakaoService;
import kr.co.sist.user.Service.LoginService;

/**
 * 사용자 로그인 요청을 처리하는 컨트롤러 클래스
 */
@Controller
public class LoginController {
    
    @Autowired
    private LoginService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private KakaoService kakaoService;

    @Value("${kakao.client-id}")
    private String kakaoClientId;
    
    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;
    
    /**
     * 일반 로그인 페이지로 이동
     */
    @GetMapping("/login")
    public String loginPage() {
        return "user/account/login";
    }
    
    /**
     * 이메일 로그인 페이지로 이동
     */
    @GetMapping("/login_email")
    public String loginEmailPage() {
        return "user/account/login_email";
    }
    
    /**
     * 일반 로그인 처리 (이메일 + 비밀번호)
     */
    @ResponseBody
    @RequestMapping(value="/loginProcess", method= { RequestMethod.POST } )
    public ResponseEntity<Map<String, String>> loginProcess(@RequestBody UserDTO loginDTO, HttpServletResponse response) {
        // 일반 로그인 처리 (비밀번호 검증 포함)
        UserDTO user = service.selectLogin(loginDTO.getEmail(), loginDTO.getPass());
        
        if( user == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // JWT 토큰 생성
        String token = jwtService.createToken(user.getUserNum());
        
        // 쿠키 설정
        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true)
            .path("/")
            .maxAge(Duration.ofHours(1))
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        
        return ResponseEntity.ok(Map.of("token", token));
    }
    
    /**
     * 회원가입 페이지로 이동
     */
    @GetMapping("/signup")
    public String signUppage() {
        return "user/account/signup";
    }

    /**
     * 카카오 로그인 요청
     */
    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&response_type=code";
        return "redirect:" + kakaoAuthUrl;
    }
    
    /**
     * 카카오 로그인 콜백 처리
     */
    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code, HttpSession session, HttpServletResponse response) {
        try {
            String accessToken = kakaoService.getAccessToken(code);
            Long kakaoId = kakaoService.getKakaoUserId(accessToken);
            
            String kakaoEmail = "kakao_" + kakaoId;
            
            // 카카오 사용자 조회 (비밀번호 검증 없음)
            UserDTO existingUser = service.findKakaoUser(kakaoEmail);
            
            if (existingUser != null) {
                // 기존 카카오 사용자 -> 바로 로그인 처리
                String token = jwtService.createToken(existingUser.getUserNum());
                
                // 바로 쿠키 설정 (세션 거치지 않음)
                ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();
                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                
                return "redirect:/";
            } else {
                // 새 사용자 -> 회원가입 페이지로
                session.setAttribute("kakaoEmail", kakaoEmail);
                return "redirect:/kakao/signup";
            }
            
        } catch (Exception e) {
            return "redirect:/login?error=kakao";
        }
    }
    
    /**
     * 카카오 회원가입 페이지
     */
    @GetMapping("/kakao/signup")
    public String kakaoSignupPage(HttpSession session, Model model) {
        String kakaoEmail = (String) session.getAttribute("kakaoEmail");
        if (kakaoEmail == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("kakaoEmail", kakaoEmail);
        return "user/account/kakao_signup";
    }
    
    /**
     * 카카오 회원가입 처리
     */
    @ResponseBody
    @PostMapping("/kakao/signupProcess")
    public ResponseEntity<Map<String, Object>> kakaoSignupProcess(@RequestBody UserDTO userDTO, 
                                                                 HttpSession session, 
                                                                 HttpServletResponse response) {
        try {
            String kakaoEmail = (String) session.getAttribute("kakaoEmail");
            if (kakaoEmail == null) {
                return ResponseEntity.ok(Map.of("success", false, "message", "세션이 만료되었습니다."));
            }
            
            // 카카오 이메일 설정
            userDTO.setEmail(kakaoEmail);
            userDTO.setPass("kakao");

            // 사용자 생성
            UserDTO newUser = service.createKakaoUser(userDTO);
            
            // JWT 토큰 생성
            String token = jwtService.createToken(newUser.getUserNum());
            
            // 쿠키 설정
            ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            
            // 세션에서 카카오 이메일 제거
            session.removeAttribute("kakaoEmail");
            
            return ResponseEntity.ok(Map.of("success", true, "message", "회원가입이 완료되었습니다."));
            
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("success", false, "message", "회원가입 중 오류가 발생했습니다."));
        }
    }
}
