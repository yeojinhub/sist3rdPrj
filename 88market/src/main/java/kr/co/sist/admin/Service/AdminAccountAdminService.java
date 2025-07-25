package kr.co.sist.admin.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.AdminDTO;
import kr.co.sist.admin.DAO.AdminAccountAdminDAO;

@Service
public class AdminAccountAdminService {

	@Autowired
	private AdminAccountAdminDAO adminDAO;
	
	/**
	 * 키워드, 계정유형에 따른 관리자 전체 조회
	 * @param keyword 검색할 키워드
	 * @param roleType 검색할 계정유형
	 * @return adminList 조회한 관리자 리스트
	 */
	public List<AdminDTO> searchKeyword(Map<String, Object> map){
		List<AdminDTO> adminList = new ArrayList<AdminDTO>();
		
	    adminList = adminDAO.selectKeyword(map); 
	    		
		return adminList;
	} //searchKeyword
	
	/**
	 * 관리자 전체 조회
	 * @return adminList 조회한 관리자 리스트
	 */
	public List<AdminDTO> searchAllAdmin(){
		List<AdminDTO> adminList = new ArrayList<AdminDTO>();
		
		adminList = adminDAO.selectAdminList();
		
		System.out.println("AccountAdminService : List 값 "+adminList);
		
		return adminList;
	} //searchAllAdmin
	
	/**
	 * 관리자 단일 조회
	 * @param admNum 조회할 관리자 번호
	 * @return adminDTO 조회한 관리자 정보
	 */
	public AdminDTO searchOneAdmin(String admNum) {
		AdminDTO adminDTO = adminDAO.selectOneAdmin(admNum);
		
		return adminDTO;
	} //searchOneAdmin
	
	/**
	 * 관리자 계정 등록
	 * @param adminDTO 등록할 관리자 정보
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean addAdmin(AdminDTO adminDTO) {
		boolean flag = false;
		
		try {
			boolean insertFlag = adminDAO.insertAdmin(adminDTO) == 1;
			
			if( insertFlag ) {
				// 등록 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 등록 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //addAdmin
	
	/**
	 * 관리자 계정 수정
	 * @param adminDTO 수정할 관리자 정보
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean modifyAdmin(AdminDTO adminDTO) {
		boolean flag = false;
		
		try {
			boolean updateFlag = adminDAO.updateAdmin(adminDTO) == 1;
			
			if( updateFlag ) {
				// 수정 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 수정 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //modifyAdmin
	
	/**
	 * 관리자 비밀번호 초기화
	 * @param admNum 수정할 관리자 번호
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean passModifyAdmin(String admNum) {
		boolean flag = false;
		
		try {
			boolean updateFlag = adminDAO.passUpdateAdmin(admNum) == 1;
			
			if( updateFlag ) {
				// 수정 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 수정 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //passModifyAdmin

} //class
