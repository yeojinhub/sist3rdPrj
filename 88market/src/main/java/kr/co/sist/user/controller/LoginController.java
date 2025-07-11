package kr.co.sist.user.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.JwtService;
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
	
	/**
	 * 일반 로그인 페이지로 이동
	 * @return "user/account/login"
	 */
	@GetMapping("/login")
	public String loginPage() {
		return "user/account/login";
	} //loginPage
	
	/**
	 * 이메일 로그인 페이지로 이동
	 * @return "user/account/login_email"
	 */
	@GetMapping("/login_email")
	public String loginEmailPage() {
		return "user/account/login_email";
	} //loginEmailPage
	
	//@GetMapping("/loginProcess")
	/**
	 * 로그인 처리 요청 프로세스
	 * @param loginDTO
	 * @param response
	 * @return 로그인 성공시 메인페이지, 실패 시 로그인 페이지로 리다이렉트
	 */
	@ResponseBody
	@RequestMapping(value="/loginProcess", method= { RequestMethod.POST } )
	public ResponseEntity<Map<String, String>> loginProcess(@RequestBody UserDTO loginDTO, HttpServletResponse response) {
		// 사용자가 입력한 이메일로 사용자 정보 조회
		UserDTO user = service.selectLogin(loginDTO.getEmail(), loginDTO.getPass());
		
		if( user == null ) {
			// 로그인 실패
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} //end if
		
		// 로그인 생성
		// JWT 토큰 생성
        String token = jwtService.createToken(user.getUserNum());
        // 생성된 토큰을 HttpOnly 쿠키에 담아 응답 헤더에 추가
        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true)
            .path("/")						// 전체 경로에서 유효
            .maxAge(Duration.ofHours(1))	// 1시간 유효
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        // token을 바디에서 포함해서 응답
        return ResponseEntity.ok(Map.of("token",token));
		
	} //loginProcess
	
	/**
	 * 회원가입 페이지로 이동
	 * @return "user/account/signup"
	 */
	@GetMapping("/signup")
	public String signUppage() {
		return "user/account/signup";
	} //signUppage

} //class
