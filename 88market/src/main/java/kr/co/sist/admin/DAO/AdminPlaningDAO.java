package kr.co.sist.admin.DAO;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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
    
    ProductDTO getProductByPrdNum(@Param("prdNum") String prdNum);
    
    void insertProductWithImages(ProductDTO productDTO, ImageDTO imageDTO);
    
    void updateProductWithImages(ProductDTO productDTO, List<MultipartFile> images) throws IOException;
    
    /* ▼ [추가] : 상품·이미지 수정용 메소드 */
    int updateProduct(ProductDTO dto);
    int updateImage(ImageDTO img);

    /** IMG_NUM 존재여부(0/1) – 새 이미지면 insert, 기존이면 update 구분 */
    int existsImage(int imgNum);
    
    ImageDTO selectImageByImgNum(@Param("imgNum") int imgNum);
    
    void deleteProductByPrdNum(@Param("prdNum") String prdNum);
    void deleteImageByPrdNum(@Param("prdNum") String prdNum);
    
}
