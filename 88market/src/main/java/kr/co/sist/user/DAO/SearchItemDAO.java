package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.ProductDTO;

@Mapper
public interface SearchItemDAO {
	
    // 메인 - 찜 많은 순 / 좋아요
	public List<ProductDTO> selectTopLikeItems(@Param("limit") int limit);
	// 메인 - 조회수 많은 목록
	public List<ProductDTO> selectTopViewItems(@Param("limit") int limit);
	// 검색 - 키워드 검색
	public List<ProductDTO> findByKeyword(@Param("keyword")String keyword);
	// 검색 - 카테고리 선택
	public List<ProductDTO> findByCategory(@Param("catNum") int catNum);
	
	// 개수 구하기
	int countByCategory(@Param("catNum") int catNum);
}
