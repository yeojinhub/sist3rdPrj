package kr.co.sist.admin.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.DTO.AdminUserProductDTO;
import kr.co.sist.admin.DAO.AdminUserProductDAO;
import kr.co.sist.admin.util.AdminSearchUserProductDTO;
import kr.co.sist.admin.util.UserProductPagination;


@Service
public class AdminUserProductService {
	  @Autowired
	  private AdminUserProductDAO aupDAO;
	  
	  // 모든 상품들 나열
//	  public List<AdminUserProductDTO> getUserProducts() {
//		  return aupDAO.selectUserProducts();
//		}//getUserProducts
	  
	  // 조건에 맞는 상품들 페이지네이션에 따라 나
	    public UserProductPagination<AdminUserProductDTO> findByAdminSearch(AdminSearchUserProductDTO dto) {
	        // 1) 전체 건수
	        int total = aupDAO.countAdminUserProducts(dto);

	        // 2) Pagination 유틸 세팅
	        UserProductPagination<AdminUserProductDTO> page = new UserProductPagination<>();
	        page.setPageNum(dto.getPageNum());
	        page.setPageSize(dto.getPageSize());
	        page.setTotalCount(total);

	        // 3) DAO 호출 파라미터 준비
	        Map<String,Object> params = new HashMap<>();
	        params.put("search",   dto);
	        params.put("startRow", page.getStartRow());
	        params.put("endRow",   page.getEndRow());

	        // 리스트에 HideType를 포한한 데이터 담기 - y,n타입
	        List<AdminUserProductDTO> items = aupDAO.searchAdminUserProducts(params);
	        List<AdminUserProductDTO> items2 =new ArrayList<AdminUserProductDTO>();
	        
	        
	        if(	!dto.getHideFilter().isEmpty()) {
	        for( AdminUserProductDTO temp : items ) {
	        	if( dto.getHideFilter().equals(temp.getProduct().getHiddenType())) {
	        		items2.add(temp);
	        	}
	        }
	        }
	        
	        page.setItems(!dto.getHideFilter().isEmpty()?items2:items);

	        return page;
	    }
	    
	    // 숨김 업데이트
	    @Transactional
	    public void updateHiddenType(List<String> prdNums, boolean hide) {
	        String hiddenValue = hide ? "Y" : "N";
	        try {
//	        	aupDAO.updateHiddenType(prdNums, hiddenValue);
	        	Map<String, Object> map = new HashMap<String, Object>();
	        	map.put("hidden", hiddenValue);
	        	map.put("prdNums",prdNums);
	        	aupDAO.updateHiddenType(map);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
	    
	    // 단일 상세페이지 이동
	    public AdminUserProductDTO findByPrdNum(String prdNum) {
	        return aupDAO.selectByPrdNum(prdNum);
	    }
	    
	    // 수정 - 상세 페이지
	    @Transactional
	    public void updateUserProduct(AdminUserProductDTO aupDTO) {
	        aupDAO.updateUserProduct(aupDTO);
	    }
	  

}
