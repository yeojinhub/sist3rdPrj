package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.LoginDAO;

public class LoginService {
	@Autowired
	private LoginDAO loginDAO;
	
	public List<UserDTO> selectLoginList() {
		return loginDAO.selectLoginList();
	} //selectLoginList
	
}
