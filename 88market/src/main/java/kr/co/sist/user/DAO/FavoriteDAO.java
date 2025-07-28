package kr.co.sist.user.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.ProductDTO;

@Repository
public class FavoriteDAO {
	
	@Autowired
    private SqlSessionTemplate sqlSession;
	
	public boolean checkFavorite(String userNum, String prdNum) {
		Map<String, String> param = new HashMap<>();
    	param.put("userNum", userNum);
    	param.put("prdNum", prdNum);
    	Integer result = sqlSession.selectOne("FavoriteMapper.checkFavorite",param);
    	return result != null && result > 0;
	}
	
	public void insertFavorite(String userNum, String prdNum) {
		Map<String, String> param = new HashMap<>();
    	param.put("userNum", userNum);
    	param.put("prdNum", prdNum);
		sqlSession.insert("FavoriteMapper.insertFavorite",param);
	}
	
	public void deleteFavorite(String userNum, String prdNum) {
		Map<String, String> param = new HashMap<>();
    	param.put("userNum", userNum);
    	param.put("prdNum", prdNum);
		sqlSession.delete("FavoriteMapper.deleteFavorite",param);
	}
	
	public List<ProductDTO> selectWishlistByUserNum(String userNum){
		System.out.println("selectWishlistByUserNum 호출 - userNum: " + userNum);
		List<ProductDTO> wishlist = sqlSession.selectList("FavoriteMapper.selectWishlistByUserNum", userNum);

		System.out.println("Wishlist size: " + (wishlist != null ? wishlist.size() : 0));
		return sqlSession.selectList("FavoriteMapper.selectWishlistByUserNum",userNum);
	}
	
	// 삭제
	public void deleteFavoriteByProduct(String prdNum) {
	    sqlSession.delete("FavoriteMapper.deleteFavoriteByProduct", prdNum);
	}
	
	
}
