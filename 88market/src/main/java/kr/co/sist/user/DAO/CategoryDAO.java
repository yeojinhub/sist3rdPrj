package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.CategoryDTO;

@Mapper
public interface CategoryDAO {

	public List<CategoryDTO> selectAllCategories();
	public CategoryDTO selectCategoryById(@Param("catNum") int catNum);
	
    // 관리자: 전체 카테고리
    List<CategoryDTO> findAll();

    // 등록
    void insert(CategoryDTO dto);

    // 수정
    void update(CategoryDTO dto);

    // 삭제
    void delete(int id);
}
