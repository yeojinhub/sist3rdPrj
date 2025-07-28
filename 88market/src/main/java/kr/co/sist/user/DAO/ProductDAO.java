package kr.co.sist.user.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.UserDTO;

@Repository
public class ProductDAO {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public int getNextProductSeq() {
        return sqlSession.selectOne("ProductMapper.getNextProductSeq");
    }

    public void insert(ProductDTO product) {
        sqlSession.insert("ProductMapper.insertProduct", product);
    }
    
    public ProductDTO selectProductByNum(String prdNum) {
    	return sqlSession.selectOne("ProductMapper.selectProductByNum",prdNum);
    }
    
    public int countChatroomsByPrdNum(String prdNum) {
    	return sqlSession.selectOne("ProductMapper.countChatroomsByPrdNum",prdNum);
    }
    
    public void increaseClickNum(@Param("prdNum")String prdNum) {
        sqlSession.update("ProductMapper.increaseClickNum", prdNum);
    }
    
    public UserDTO getUser(@Param("userNum")String userNum) {
    	return sqlSession.selectOne("ProductMapper.getUser",userNum);
    }
    
    public int getSafeByUserNum(@Param("userNum")String userNum) {
    	return sqlSession.selectOne("ProductMapper.getSafeByUserNum",userNum);
    }
    
    public int getReviewByUserNum(@Param("userNum")String userNum) {
    	return sqlSession.selectOne("ProductMapper.getReviewByUserNum",userNum);
    }
    
    public ProductDTO selectRandomProduct(String userNum, String excludePrdNum) {
    	Map<String, String> param = new HashMap<>();
    	param.put("userNum", userNum);
    	param.put("excludePrdNum", excludePrdNum);
    	return sqlSession.selectOne("ProductMapper.selectRandomProduct",param);
    }
    
    public List<ProductDTO> selectRelatedProducts(int catNum, String prdNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("catNum", catNum);
        params.put("prdNum", prdNum);
        return sqlSession.selectList("ProductMapper.selectRelatedProducts", params);
    }
    
    public void increaseLikeNum(String prdNum) {
    	sqlSession.update("ProductMapper.increaseLikeNum",prdNum);
    }
    
    public void decreaseLikeNum(String prdNum) {
    	sqlSession.update("ProductMapper.decreaseLikeNum",prdNum);
    }
    
    //민경 - 마이페이지 내상품 
    // 상품
    public List<ProductDTO> selectByLoginId(String loginId) {
        Map<String, String> param = new HashMap<>();
        param.put("loginId", loginId);
        return sqlSession.selectList("ProductMapper.selectByLoginId", param);
    }

    // 유저 정보
    public UserDTO getUserByLoginId(String email) {
        Map<String, String> param = new HashMap<>();
        param.put("email", email);
        return sqlSession.selectOne("ProductMapper.getUserByLoginId", param);
    }

    // 안전거래 건수
    public int getSafeByLoginId(String email) {
        Map<String, String> param = new HashMap<>();
        param.put("email", email);
        return sqlSession.selectOne("ProductMapper.getSafeByLoginId", param);
    }

    // 거래후기 건수
    public int getReviewByLoginId(String email) {
        Map<String, String> param = new HashMap<>();
        param.put("email", email);
        return sqlSession.selectOne("ProductMapper.getReviewByLoginId", param);
    }
    
    // 민경 판매상품 삭제
    public void deleteProduct(String prdNum) {
        sqlSession.delete("ProductMapper.deleteProduct", prdNum);  
    }
}

