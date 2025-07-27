package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.sist.DTO.CompanyDTO;

@Mapper
public interface AdminAccountCompanyDAO {
	
	/**
	 * 키워드, 계정유형에 따른 기업 전체 조회
	 * @param map 검색할 키워드, 검색할 계정유형
	 * @return 조회한 기업 리스트
	 */
	public List<CompanyDTO> selectKeyword(Map<String, Object> map);
	/**
	 * 전체 기업 조회
	 * @return 조회한 기업 리스트
	 */
	public List<CompanyDTO> selectCompanyList();
	/**
	 * 단일 기업 조회
	 * @param comNum 조회할 기업 번호
	 * @return 조회한 기업 정보
	 */
	public CompanyDTO selectOneCompany(String comNum);
	
	/**
	 * 기업 계정 등록
	 * @param companyDTO 등록할 기업 정보
	 * @return int 성공시 true, 실패 시 false 반환
	 */
	public int insertCompany(@RequestBody CompanyDTO companyDTO);
	
	/**
	 * 기업 계정 수정
	 * @param companyDTO 수정할 기업 정보
	 * @return int 성공시 true, 실패 시 false 반환
	 */
	public int updateCompany(@RequestBody CompanyDTO companyDTO);

	/**
	 * 기업 비밀번호 초기화
	 * @param comNum 조회할 기업 번호
	 * @return int 성공시 true, 실패 시 false 반환
	 */
	public int passUpdateCompany(String comNum);
	
} //interface
