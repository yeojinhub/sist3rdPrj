package kr.co.sist.user.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.CategoryWithProductWithFavoriteWithCompanyWithImageDTO;
import kr.co.sist.DTO.CompanyWithProductDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.ReviewDTO;

@Mapper
public interface PlaningDAO {

	List<CompanyWithProductDTO> selectPlaningList();
	
	List<ProductDTO> selectProductsByComNum(@Param("comNum") String comNum);
	
	List<CategoryWithProductWithFavoriteWithCompanyWithImageDTO> onePlaningList(@Param("prdNum") String prdNum);
	
	List<Map<String, Object>> selectRandomProductsByComNum(@Param("comNum") String comNum, @Param("productId") String productId);

	
	int CompanyProductsAllCount(@Param("comNum") String comNum);
	
	int CompanyProductsSellCount(@Param("comNum") String comNum);
	
	int CompanyProductsSoldOutCount(@Param("comNum") String comNum);
	
	List<ProductDTO> ProductsByCompanyAndSellType(@Param("comNum") String comNum,
            @Param("sellType") String sellType);
	List<ProductDTO> AllProductsByCompany(@Param("comNum") String comNum);
	
	List<ProductDTO> ProductsByCompanyAndSellTypeSort(@Param("comNum") String comNum,
			@Param("sellType") String sellType,String sort);
	List<ProductDTO> AllProductsByCompanySort(@Param("comNum") String comNum,String sort);
	// 리뷰 목록 조회
    List<ReviewDTO> selectReview(@Param("prdNum") String prdNum);
    // 리뷰 개수 조회
    int selectReviewCount(@Param("prdNum") String prdNum);
    
    List<Map<String, Object>> selectProductsByComNumWithImage(@Param("comNum") String comNum);
    
    List<Map<String, Object>> selectProductsWithImageByComNum(@Param("comNum") String comNum);
    //조회수
    int increaseClick(@Param("prdNum") String prdNum);
	
}
