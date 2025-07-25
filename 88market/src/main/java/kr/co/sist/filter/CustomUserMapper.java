package kr.co.sist.filter;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.UserDTO;

@Mapper
public interface CustomUserMapper {
    UserDTO selectUserByUserNum(String userNum);
}
