package kr.co.sist.admin.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.AddressDTO;
import kr.co.sist.DTO.AdminDTO;
import kr.co.sist.DTO.BankDTO;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.admin.DAO.AdminAccountUserDAO;

@Service
public class AdminAccountUserService {

	@Autowired
	private AdminAccountUserDAO userDAO;
	
	/**
	 * 회원 전체 조회
	 * @return userList 조회한 회원 리스트
	 */
	public List<UserDTO> searchAllUser(){
		List<UserDTO> userList = new ArrayList<UserDTO>();
		
		userList = userDAO.selectUserList();
		
		System.out.println("UserAccountService : List 값 "+userList);
		
		return userList;
	} //searchAllUser
	
	/**
	 * 회원 단일 조회
	 * @param userNum 조회할 회원 번호
	 * @return userDTO 조회한 회원 정보
	 */
	public UserDTO searchOneUser(String userNum) {
		UserDTO userDTO = userDAO.selectOneUser(userNum);
		
		return userDTO;
	} //selectOneUser
	
	/**
	 * 회원 계정 등록
	 * @param userDTO 등록할 회원 정보
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean addUser(UserDTO userDTO) {
		boolean flag = false;
		
		try {
			boolean insertFlag = userDAO.insertUser(userDTO) == 1;
			
			if( insertFlag ) {
				// 등록 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 등록 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //addUser
	
	/**
	 * 회원 계정 수정
	 * @param userDTO 수정할 회원 정보
	 * @return flag 성공시 true, 실패 시 false 반환
	 */
	public boolean modifyUser(UserDTO userDTO) {
		boolean flag = false;
		
		try {
			boolean updateFlag = userDAO.updateUser(userDTO) == 1;
			
			if( updateFlag ) {
				// 수정 성공
				flag = true;
			} //end if
		} catch(Exception e) {
			// 수정 실패
			e.printStackTrace();
		} //end try catch
		
		return flag;
	} //modifyUser

	/**
	 * 계좌 전체 조회
	 * @param userNum 조회할 회원 번호
	 * @return addressList 조회한 계좌 리스트
	 */
	public List<BankDTO> searchAllBank(String userNum){
		List<BankDTO> bankList = new ArrayList<BankDTO>();
		
		bankList = userDAO.selectBankList(userNum);
		
		return bankList;
	} //searchAllBank
	
	/**
	 * 배송지 전체 조회
	 * @param userNum 조회할 회원 번호
	 * @return addressList 조회한 배송지 리스트
	 */
	public List<AddressDTO> searchAllAddress(String userNum){
		List<AddressDTO> addressList = new ArrayList<AddressDTO>();
		
		addressList = userDAO.selectAddressList(userNum);
		
		return addressList;
	} //selectAllAddress
	
	/**
	 * 관리자 전체 조회
	 * @return adminList 조회한 관리자 리스트
	 */
	public List<AdminDTO> searchAllAdmin(){
		List<AdminDTO> adminList = new ArrayList<AdminDTO>();
		
		//adminList = userDAO.selectUserList();
		
		System.out.println("UserAccountService : List 값 "+adminList);
		
		return adminList;
	} //searchAllAdmin
	
} //class
