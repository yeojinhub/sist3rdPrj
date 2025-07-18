package kr.co.sist.user.controller;

import java.time.Duration;
import java.util.*;

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
import kr.co.sist.user.Service.NaverService;

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

    @Autowired
    private NaverService naverService;
    
    @Value("${naver.client-id}")
    private String naverClientId;
    
    @Value("${naver.redirect-uri}")
    private String naverRedirectUri;

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

    /**
     * 네이버 로그인 요청
     */
    @GetMapping("/naver/login")
    public String naverLogin(HttpSession session) {
        // CSRF 방지용 state 생성
        String state = UUID.randomUUID().toString();
        session.setAttribute("oauth_state", state);
        
        String naverAuthUrl = "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + naverClientId
                + "&redirect_uri=" + naverRedirectUri
                + "&state=" + state;
        return "redirect:" + naverAuthUrl;
    }
    
    /**
     * 네이버 로그인 콜백 처리
     */
    @GetMapping("/naver/callback")
    public String naverCallback(@RequestParam String code, 
                               @RequestParam String state,
                               HttpSession session, 
                               HttpServletResponse response) {
        try {
        System.out.println("=== 네이버 로그인 콜백 처리 시작 ===");
        System.out.println("code: " + code);
        System.out.println("state: " + state);
        
        // state 검증
        String sessionState = (String) session.getAttribute("oauth_state");
        System.out.println("session state: " + sessionState);
        
        if (!state.equals(sessionState)) {
            System.out.println("ERROR: state 불일치");
            return "redirect:/login?error=state_mismatch";
        }
        
        System.out.println("액세스 토큰 요청 시작...");
        String accessToken = naverService.getAccessToken(code, state);
        System.out.println("액세스 토큰 받음: " + accessToken);
            Map<String, Object> userInfo = naverService.getUserInfo(accessToken);
            
            // 네이버 ID를 이메일 형태로 변환
            String naverId = (String) userInfo.get("id");
            String naverEmail = "naver_" + naverId;
            
            UserDTO existingUser = service.findNaverUser(naverEmail);
            
            if (existingUser != null) {
                // 기존 사용자 -> 로그인 처리
                String token = jwtService.createToken(existingUser.getUserNum());
                
                ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();
                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                
                return "redirect:/";
            } else {
                // 새 사용자 -> 회원가입 페이지로
                session.setAttribute("naverEmail", naverEmail);
                session.setAttribute("naverUserInfo", userInfo);
                return "redirect:/naver/signup";
            }
            
        } catch (Exception e) {
        System.out.println("ERROR: 네이버 로그인 콜백 처리 중 예외 발생");
        System.out.println("예외 메시지: " + e.getMessage());
        e.printStackTrace();
        return "redirect:/login?error=naver";
    }
    }
    
    /**
     * 네이버 회원가입 페이지
     */
    @GetMapping("/naver/signup")
    public String naverSignupPage(HttpSession session, Model model) {
        String naverEmail = (String) session.getAttribute("naverEmail");
        if (naverEmail == null) {
            return "redirect:/login";
        }
        
        Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("naverUserInfo");
        model.addAttribute("naverEmail", naverEmail);
        model.addAttribute("naverUserInfo", userInfo);
        return "user/account/naver_signup";
    }
    
    /**
     * 네이버 회원가입 처리
     */
    @ResponseBody
    @PostMapping("/naver/signupProcess")
    public ResponseEntity<Map<String, Object>> naverSignupProcess(@RequestBody UserDTO userDTO, 
                                                                 HttpSession session, 
                                                                 HttpServletResponse response) {
        try {
            String naverEmail = (String) session.getAttribute("naverEmail");
            if (naverEmail == null) {
                return ResponseEntity.ok(Map.of("success", false, "message", "세션이 만료되었습니다."));
            }
            
            // 네이버 이메일 설정
            userDTO.setEmail(naverEmail);
            userDTO.setPass("naver");
            
            // 사용자 생성
            UserDTO newUser = new UserDTO();

            try {
                newUser = service.createNaverUser(userDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // JWT 토큰 생성
            String token = jwtService.createToken(newUser.getUserNum());
            
            // 쿠키 설정
            ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            
            // 세션 정리
            session.removeAttribute("naverEmail");
            session.removeAttribute("naverUserInfo");
            
            return ResponseEntity.ok(Map.of("success", true, "message", "회원가입이 완료되었습니다."));
            
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("success", false, "message", "회원가입 중 오류가 발생했습니다."));
        }
    }
}
