package kr.co.sist.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;

@Mapper
@Repository
public interface AdminPlaningDAO {

	
	String selectComNumById(String id);
	List<ProductDTO> selectProductsByComNum(@Param("comNum") String comNum);
	
    // 조건에 맞는 상품 목록 조회 (페이징 포함)
	List<ProductDTO> searchProductsByCondition(
        @Param("comNum") String comNum,
        @Param("keyword") String keyword,
        @Param("hidden") String hidden,
        @Param("startRow") int startRow,
        @Param("endRow") int endRow
    );

    // 총 상품 수 조회 (조건 포함)
	int getTotalCountByCondition(
        @Param("comNum") String comNum,
        @Param("keyword") String keyword,
        @Param("hidden") String hidden
    );
	
	List<CategoryDTO> selectAllCategories();
	
	int insertProduct(ProductDTO product);

    int insertImage(ImageDTO image);
    
    int getNextProductSeq();
    
    int getNextImageSeq();
    
}
