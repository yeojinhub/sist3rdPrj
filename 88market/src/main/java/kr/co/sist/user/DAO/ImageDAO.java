package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.ImageDTO;

@Mapper
@Repository
public class ImageDAO {
	@Autowired
    private SqlSessionTemplate sqlSession;

    public void insertImage(ImageDTO image) {
        sqlSession.insert("ImageMapper.insertImage", image);
    }

    public int getNextImageSeq() {
        return sqlSession.selectOne("ImageMapper.getNextImageSeq");
    }
}
