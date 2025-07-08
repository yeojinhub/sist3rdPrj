package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.CategoryDTO;

@Mapper
public interface CategoryDAO {

	List<CategoryDTO> selectAllCategories();
	CategoryDTO selectCategoryById(@Param("catNum") int catNum);
	
	
}
