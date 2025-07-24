package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.sist.DTO.AdminDTO;

@Mapper
public interface AdminAccountAdminDAO {
	
	/**
	 * 키워드, 계정유형에 따른 관리자 전체 조회
	 * @return 조회한 관리자 리스트
	 */
	public List<AdminDTO> selectKeyword(Map<String, Object> map);
	/**
	 * 전체 관리자 조회
	 * @return 조회한 관리자 리스트
	 */
	public List<AdminDTO> selectAdminList();
	/**
	 * 단일 관리자 조회
	 * @param admNum 조회할 관리자 번호
	 * @return 조회한 관리자 정보
	 */
	public AdminDTO selectOneAdmin(String admNum);
	
	/**
	 * 관리자 계정 등록
	 * @param adminDTO 등록할 관리자 정보
	 * @return int 성공시 true, 실패 시 false 반환
	 */
	public int insertAdmin(@RequestBody AdminDTO adminDTO);
	/**
	 * 관리자 계정 수정
	 * @param adminDTO 수정할 관리자 정보
	 * @return int 성공시 true, 실패 시 false 반환
	 */
	public int updateAdmin(@RequestBody AdminDTO adminDTO);
	/**
	 * 관리자 비밀번호 초기화
	 * @param admNum 조회할 관리자 번호
	 * @return int 성공시 true, 실패 시 false 반환
	 */
	public int passUpdateAdmin(String admNum);
	
} //interface
