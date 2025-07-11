package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.LoginDAO;

/**
 * 로그인 관련 비즈니스 로직을 담당하는 서비스 클래스
 */
@Service
public class LoginService {
	
	@Autowired
	private LoginDAO loginDAO;
	
	/**
	 * @param email 입력받은 이메일
	 * @param pass 입력받은 비밀번호
	 * @return 로그인 성공시 UserDTO 반환, 실패 시 null
	 */
	public UserDTO selectLogin(String email, String pass) {
		// 사용자에게 입력받은 이메일로 사용자 정보 조회
		UserDTO loginDTO = loginDAO.selectLoginList(email);
		
		// 사용자 정보가 존재하고 비밀번호 일치 시
		if( loginDTO != null && loginDTO.getPass().equals(pass) ) {
		// 로그인 성공
			return loginDTO;
		} //end if
		
		// 로그인 실패
		return null;
	} //selectLoginList
	
} //class
