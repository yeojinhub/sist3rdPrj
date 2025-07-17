package kr.co.sist.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.UserDTO;

@Mapper
public interface AdminAccountUserDAO {
	
	public List<UserDTO> selectUserList();
	
} //interface
