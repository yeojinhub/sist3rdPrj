package kr.co.sist.admin.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.admin.DAO.AdminAccountUserDAO;

@Service
public class AdminAccountUserService {

	@Autowired
	private AdminAccountUserDAO userDAO;
	
	public List<UserDTO> selectAllUser(){
		List<UserDTO> userList = new ArrayList<UserDTO>();
		
		userList = userDAO.selectUserList();
		
		System.out.println("UserAccountService : List 값 "+userList);
		
		return userList;
	} //selectAllUser
	
} //class
