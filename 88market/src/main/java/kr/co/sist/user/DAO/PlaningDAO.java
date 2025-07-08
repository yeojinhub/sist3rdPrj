package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO;
import kr.co.sist.DTO.CompanyWithProductDTO;
import kr.co.sist.DTO.ProductDTO;

@Mapper
public interface PlaningDAO {

	List<CompanyWithProductDTO> selectPlaningList();
	List<ProductDTO> selectProductsByComNum(@Param("comNum") String comNum);
	List<CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO> onePlaningList(@Param("prdNum") String prdNum);
	List<ProductDTO> selectRandomProductsByComNum(@Param("comNum") String comNum,String productId);
	int CompanyProductsAllCount(@Param("comNum") String comNum);
	int CompanyProductsSellCount(@Param("comNum") String comNum);
	int CompanyProductsSoldOutCount(@Param("comNum") String comNum);
	List<ProductDTO> ProductsByCompanyAndSellType(@Param("comNum") String comNum,
            @Param("sellType") String sellType);
	List<ProductDTO> AllProductsByCompany(@Param("comNum") String comNum);
	
}
