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
		
		System.out.println("SignUpService : DTO 값 "+ userDTO);
		flag = signDAO.addUser(userDTO);
		
		System.out.println("SignUpService : flag 값 "+flag);
		
		return flag;
	} //addMember
} //class
