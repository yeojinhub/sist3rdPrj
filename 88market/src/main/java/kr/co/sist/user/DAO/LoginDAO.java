package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.UserDTO;

@Mapper
public interface LoginDAO {
	
	public UserDTO selectLoginList(String email);
	public UserDTO login(UserDTO loginDTO);
	 
} //interface
