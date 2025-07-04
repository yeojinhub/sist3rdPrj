package kr.co.sist.user.controller;

import java.time.Duration;

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

import jakarta.servlet.http.HttpServletResponse;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.JwtService;
import kr.co.sist.user.Service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService service;
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/login_email")
	public String loginEmailPage() {
		return "user/account/login_email";
	} //loginEmailPage
	
	//@GetMapping("/loginProcess")
	@RequestMapping(value="/loginProcess", method= { RequestMethod.POST } )
	public ResponseEntity<Void> loginProcess(@RequestBody UserDTO loginDTO, HttpServletResponse response) {
		UserDTO user = service.selectLogin(loginDTO.getEmail(), loginDTO.getPass());
		
		if( user == null ) {
//			session.setAttribute("loginDTO", loginDTO);
//			System.out.println("로그인 되었습니다.");
//			return "redirect:/";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} 
		
        // 1) 토큰 생성
        String token = jwtService.createToken(user.getEmail());
        // 2) HttpOnly 쿠키에 담아서 응답 헤더에 추가
        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true)
            .path("/")
            .maxAge(Duration.ofHours(1))
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        // 3) 바디는 비워두고 200 OK
        return ResponseEntity.ok().build();
		
	} //loginProcess

} //class
