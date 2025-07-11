package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.UserDTO;

/**
 * 로그인 관련 데이터 접근 인터페이스
 * MyBatis로 인터페이스의 구현체를 자동으로 생성하여 DB와 연결
 */
@Mapper
public interface LoginDAO {
	
	/**
	 * 이메일을 기반으로 사용자 정보를 조회하는 메서드
	 * @param email 조회할 사용자의 이메일
	 * @return 해당 이메일을 가진 사용자의 정보 UserDTO 반환
	 */
	public UserDTO selectLoginList(@Param("email") String email);
	 
} //interface
