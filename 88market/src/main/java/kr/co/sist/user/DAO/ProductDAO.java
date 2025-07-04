package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.ProductDTO;

@Repository
@Mapper
public class ProductDAO {

	@Autowired
    private SqlSessionTemplate sqlSession;

    public void insertProduct(ProductDTO product) {
        sqlSession.insert("ProductMapper.insertProduct", product);
    }

    public int getNextProductSeq() {
        return sqlSession.selectOne("ProductMapper.getNextProductSeq");
    }
	
}
