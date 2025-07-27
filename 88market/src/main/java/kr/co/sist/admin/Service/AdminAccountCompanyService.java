package kr.co.sist.admin.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.CompanyDTO;
import kr.co.sist.admin.DAO.AdminAccountCompanyDAO;

@Service
public class AdminAccountCompanyService {

	@Autowired
	private AdminAccountCompanyDAO comDAO;
	
	/**
	 * 키워드, 계정유형에 따른 기업 전체 조회
	 * @param map 검색할 키워드, 검색할 계정유형
	 * @return companyList 조회한 기업 리스트
	 */
	public List<CompanyDTO> searchKeyword(Map<String, Object> map){
		List<CompanyDTO> companyList = new ArrayList<CompanyDTO>();
		
		companyList = comDAO.selectKeyword(map); 
	    		
		return companyList;
	} //searchKeyword
	
	/**
	 * 기업 전체 조회
	 * @return companyList 조회한 기업 리스트
	 */
	public List<CompanyDTO> searchAllCompany(){
		List<CompanyDTO> companyList = new ArrayList<CompanyDTO>();
		
		companyList = comDAO.selectCompanyList();
		
		System.out.println("CompanyService : List 값 "+companyList);
		
		return companyList;
	} //searchAllCompany
	
	/**
	 * 기업 단일 조회
	 * @param comNum 조회할 기업 번호
	 * @return companyDTO 조회한 기업 정보
	 */
	public CompanyDTO searchOneCompany(String comNum) {
		CompanyDTO companyDTO = comDAO.selectOneCompany(comNum);
		
		return companyDTO;
	} //searchOneCompany
	
	/**
	 * 기업 계정 등록
	 * @param companyDTO 등록할 기업 정보
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean addCompany(CompanyDTO companyDTO) {
		boolean flag = false;
		
		try {
			boolean insertFlag = comDAO.insertCompany(companyDTO) == 1;
			
			if( insertFlag ) {
				// 등록 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 등록 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //addCompany
	
	/**
	 * 기업 계정 수정
	 * @param companyDTO 수정할 기업 정보
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean modifyCompany(CompanyDTO companyDTO) {
		boolean flag = false;
		
		try {
			boolean updateFlag = comDAO.updateCompany(companyDTO) == 1;
			
			if( updateFlag ) {
				// 수정 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 수정 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //modifyCompany
	
	/**
	 * 기업 비밀번호 초기화
	 * @param comNum 수정할 기업 번호
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean passModifyCompany(String comNum) {
		boolean flag = false;
		
		try {
			boolean updateFlag = comDAO.passUpdateCompany(comNum) == 1;
			
			if( updateFlag ) {
				// 수정 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 수정 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //passModifyCompany

} //class
