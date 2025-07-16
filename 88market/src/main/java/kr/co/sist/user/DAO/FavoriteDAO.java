package kr.co.sist.user.DAO;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteDAO {
	
	@Autowired
    private SqlSessionTemplate sqlSession;
	
	public boolean checkFavorite(String userNum, String prdNum) {
		Map<String, String> param = new HashMap<>();
    	param.put("userNum", userNum);
    	param.put("prdNum", prdNum);
    	return sqlSession.selectOne("FavoriteMapper.checkFavorite",param);
	}
	
	public void insertFavorite(String userNum, String prdNum) {
		Map<String, String> param = new HashMap<>();
    	param.put("userNum", userNum);
    	param.put("prdNum", prdNum);
		sqlSession.insert("FavoriteMapper.insertFavorite",param);
	}
	
	public void increaseLikeNum(String prdNum) {
		sqlSession.update("FavoriteMapper.increaseLikeNum",prdNum);
	}
}
