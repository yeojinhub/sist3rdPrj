package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.CategoryDTO;

@Repository
@Mapper
public class CategoryDAO {

    @Autowired
    private SqlSession sqlSession;

    public List<CategoryDTO> selectAllCategory() {
        return sqlSession.selectList("CategoryMapper.selectAllCategory");
    }
}

