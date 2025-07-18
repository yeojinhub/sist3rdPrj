package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.sist.DTO.UserDTO;

@Mapper
public interface SignUpDAO {
	
	public int addUser(@RequestBody UserDTO userDTO);
	public int addAddress(@RequestBody UserDTO userDTO);

} //inserface
