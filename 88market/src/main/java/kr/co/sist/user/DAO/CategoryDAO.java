package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.CategoryDTO;

public interface CategoryDAO {

	List<CategoryDTO> selectAllCategories();
	CategoryDTO selectCategoryById(@Param("catNum") int catNum);
	
}
