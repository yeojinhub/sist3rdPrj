package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.UserDTO;

@Mapper
public interface SellerDAO {

	// 유저 정보 싹다 가져와
	public UserDTO selectSellerInfoByUserNum(String userNum);
	
	// 유저 상품 몇건이야
	public List<ProductDTO> selectTradeByUserNum(String userNum);
	
	// 이미지 담자
	public ImageDTO selectImgByImgNum(Integer imgNum);
	
}// interface
