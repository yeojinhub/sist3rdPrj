package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.DAO.CategoryDAO;
import kr.co.sist.user.DAO.SearchItemDAO;

@Service
public class SearchItemService {

	@Autowired(required = false)
	private SearchItemDAO siDAO;
	
    @Autowired
    private CategoryDAO categoryDAO;
    
	//찜
    public List<ProductDTO> getTopLikeItems(int limit) {
        return siDAO.selectTopLikeItems(limit);
    }
    
    // 카테고리 - 15개 랜덤 
    public List<ProductDTO> findByCategoryRandom(int catNum, int limit) {
        return siDAO.selectRandomCategory(catNum, limit);
    }
    
	//조회수
	public List<ProductDTO> getTopViewItems(int limit){
		return siDAO.selectTopViewItems(limit);
	}//getTopViewItems
	
	//검색창 - 키워드
	public List<ProductDTO> findByKeyword(String keyword){
		return siDAO.findByKeyword(keyword==null?"":keyword);
	}//findByKeyword
	
    //카테고리 번호로 상품 검색 
    public List<ProductDTO> findByCategory(int catNum) {
        return siDAO.findByCategory(catNum);
    }
    
    // 카테고리/키워드 필터 검색
    public  List<ProductDTO> findByKeywordAndFilters(String Keyword, Integer catNum,Integer minPrice, Integer maxPrice, String tradeOption, String sortOption){
    	return siDAO.findByKeywordAndFilters(Keyword,catNum,minPrice,maxPrice,tradeOption,sortOption);
    }
    

	
    /** 카테고리번호→카테고리명, Null 우회하기 */
    public String getCategoryName(Integer catNum) {
    	
    	if(catNum==null) {
    		return "전체";
    	}
        CategoryDTO dto = categoryDAO.selectCategoryById(catNum);
        return dto != null ? dto.getName() : "전체";
    }
	
}//class








