package kr.co.sist.user.DAO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.ProductDTO;

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
}

