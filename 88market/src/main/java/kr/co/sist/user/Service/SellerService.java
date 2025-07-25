package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.SellerDAO;

@Service
public class SellerService {

	@Autowired
	private SellerDAO sDAO;
	
	/**
	 * 유저 정보 사사삭
	 * @param userNum
	 * @return
	 */
	public UserDTO searchUserInfoByUserNum(String userNum) {
		return sDAO.selectSellerInfoByUserNum(userNum);
	}// searchUserInfoByUserNum
	
}// class
