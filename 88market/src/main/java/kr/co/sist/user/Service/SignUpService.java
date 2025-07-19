package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.SignUpDAO;

@Service
public class SignUpService {
	
	@Autowired
	private SignUpDAO signDAO;
	
	@Transactional
	public boolean addMember(UserDTO userDTO) {
		boolean flag = false;
		
		System.out.println("SignUpService : DTO 값 "+ userDTO);
		try { 
			boolean userFlag = signDAO.addUser(userDTO) == 1;
			boolean addressFlag = signDAO.addAddress(userDTO) == 1;
			
			// insert 오류
			if( !userFlag || !addressFlag ) {
				// 트랜잭션 사용하기 위해 롤백 처리
				throw new RuntimeException("회원가입 insert 실패");
			} else {
				// insert 성공
				flag = true;
			} //end if else
			
		} catch(Exception e) {
			e.printStackTrace();
			// 트랜잭션 사용하기 위해 롤백 처리
			flag = false;
			
			throw new RuntimeException("회원가입 실패 오류 :", e);
		} //end try catch
		
		System.out.println("SignUpService : flag 값 "+flag);
		
		return flag;
	} //addMember
} //class
