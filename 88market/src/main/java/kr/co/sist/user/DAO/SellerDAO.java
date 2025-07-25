package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.UserDTO;

@Mapper
public interface SellerDAO {

	// 유저 정보 싹다 가져와
	public UserDTO selectSellerInfoByUserNum(String userNum);
	
	// 유저 안전거래 몇건이야
	public Integer selectSafeTradeByUserNum(String userNum);
	
}// interface
