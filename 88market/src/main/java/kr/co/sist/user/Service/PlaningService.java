package kr.co.sist.user.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO;
import kr.co.sist.DTO.CompanyWithProductDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.DAO.PlaningDAO;

@Service
public class PlaningService {

	@Autowired
	private PlaningDAO planingDAO;
	
	public List<CompanyWithProductDTO> getCompanyNames(){
		return planingDAO.selectPlaningList();
	}
	
	public List<ProductDTO> getProductListByComNum(String comNum) {
        return planingDAO.selectProductsByComNum(comNum);
    }
	
	public List<CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO> getCompanyProduct(String prdNum){
		return planingDAO.onePlaningList(prdNum);
	}
	
	public List<ProductDTO> getRandomProductsByComNum(String comNum, String productId){
		return planingDAO.selectRandomProductsByComNum(comNum, productId);
	}
	
	public int getCompanyProductsAllCount(String comNum){
		return planingDAO.CompanyProductsAllCount(comNum);
	}
	public int getCompanyProductsSellCount(String comNum){
		return planingDAO.CompanyProductsSellCount(comNum);
	}
	public int getCompanyProductsSoldOutCount(String comNum){
		return planingDAO.CompanyProductsSoldOutCount(comNum);
	}
	
	public List<ProductDTO> ProductsByCompanyAndSellType(@Param("comNum") String comNum,
            @Param("sellType") String sellType){
		return planingDAO.ProductsByCompanyAndSellType(comNum, sellType);
	}
	
	public List<ProductDTO> AllProductsByCompany(@Param("comNum") String comNum){
		return planingDAO.AllProductsByCompany(comNum);
	}
	
}
