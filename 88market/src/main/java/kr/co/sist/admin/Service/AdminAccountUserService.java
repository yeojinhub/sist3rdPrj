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
	
	/**
	 * 회원 전체 조회
	 * @return
	 */
	public List<UserDTO> searchAllUser(){
		List<UserDTO> userList = new ArrayList<UserDTO>();
		
		userList = userDAO.selectUserList();
		
		System.out.println("UserAccountService : List 값 "+userList);
		
		return userList;
	} //searchAllUser
	
	/**
	 * 회원 단일 조회
	 * @param userNum
	 * @return
	 */
	public UserDTO searchOneUser(String userNum) {
		UserDTO userDTO = userDAO.selectOneUser(userNum);
		
		return userDTO;
	} //selectOneUser
	
} //class
