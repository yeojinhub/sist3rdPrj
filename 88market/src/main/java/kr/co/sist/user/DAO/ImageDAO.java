package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.ImageDTO;

@Repository
public class ImageDAO {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public int getNextImageSeq() {
        return sqlSession.selectOne("ImageMapper.getNextImageSeq");
    }

    public void insert(ImageDTO image) {
        sqlSession.insert("ImageMapper.insertImage", image);
    }
    
    public ImageDTO selectImageByNum(@Param("imgNum")int imgNum) {
    	return sqlSession.selectOne("ImageMapper.selectImageByNum",imgNum);
    }
}

