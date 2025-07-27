package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.sist.DTO.AddressDTO;
import kr.co.sist.DTO.BankDTO;
import kr.co.sist.DTO.UserDTO;

@Mapper
public interface AdminAccountUserDAO {
	
	/**
	 * 키워드, 계정유형에 따른 회원 전체 조회
	 * @param map 검색할 키워드, 검색할 계정유형
	 * @return 조회한 회원 리스트
	 */
	public List<UserDTO> selectKeyword(Map<String, Object> map);
	/**
	 * 전체 회원 조회
	 * @return 조회한 회원 리스트
	 */
	public List<UserDTO> selectUserList();
	/**
	 * 단일 회원 조회
	 * @param userNum 조회할 회원 번호
	 * @return 조회한 회원 정보
	 */
	public UserDTO selectOneUser(String userNum);
	
	/**
	 * 회원 계정 등록
	 * @param userDTO 등록할 회원 정보
	 * @return 성공시 true, 실패 시 false 반환
	 */
	public int insertUser(@RequestBody UserDTO userDTO);
	/**
	 * 회원 주소 등록
	 * @param userDTO 등록할 주소 정보
	 * @return 성공시 true, 실패 시 false 반환
	 */
	public int insertAddress(@RequestBody UserDTO userDTO);
	/**
	 * 회원 계정 수정
	 * @param userDTO 수정할 회원 정보
	 * @return 성공시 true, 실패 시 false 반환
	 */
	public int updateUser(@RequestBody UserDTO userDTO);
	
	/**
	 * 전체 계좌 조회
	 * @param userNum 조회할 회원 번호
	 * @return 조회한 계좌 리스트
	 */
	public List<BankDTO> selectBankList(String userNum);
	/**
	 * 전체 배송지 조회
	 * @param userNum 조회할 회원 번호
	 * @return 조회한 배송지 리스트
	 */
	public List<AddressDTO> selectAddressList(String userNum);
	
} //interface
