package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.sist.DTO.UserDTO;

@Mapper
public interface SignUpDAO {
	
	public boolean addUser(@RequestBody UserDTO userDTO);

} //inserface
