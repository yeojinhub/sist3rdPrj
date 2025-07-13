package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.SignUpDAO;

@Service
public class SignUpService {
	
	@Autowired
	private SignUpDAO signDAO;
	
	public boolean addMember(UserDTO userDTO) {
		boolean flag = false;
		
		flag = signDAO.addUser(userDTO);
		
		return flag;
	} //addMember
} //class
