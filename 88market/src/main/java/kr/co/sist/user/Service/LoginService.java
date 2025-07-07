package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.LoginDAO;

@Service
public class LoginService {
	@Autowired
	private LoginDAO loginDAO;
	
	/**
	 * @param email 입력받은 이메일
	 * @return null이 아닐 때 loginDTO 반환, null일 경우 그대로 반환
	 */
	public UserDTO selectLogin(String email, String pass) {
		System.out.println("LoginService "+email+pass);
		UserDTO loginDTO = loginDAO.selectLoginList(email);
		System.out.println("LoginService "+loginDTO);
		
		if( loginDTO != null && loginDTO.getPass().equals(pass) ) {
			System.out.println("LoginService "+loginDTO+loginDTO.getPass());
			return loginDTO;
		} //end if
		
		return null;
	} //selectLoginList
	
} //class
