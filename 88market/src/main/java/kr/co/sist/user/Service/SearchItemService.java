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
	
    /** 카테고리번호→카테고리명 */
    public String getCategoryName(int catNum) {
        CategoryDTO dto = categoryDAO.selectCategoryById(catNum);
        return dto != null ? dto.getName() : "전체";
    }
	
}//class
