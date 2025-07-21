package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.AdminUserProductDTO;
import kr.co.sist.admin.util.AdminSearchUserProductDTO;

@Mapper
public interface AdminUserProductDAO {
	
    // 전체 카운트
    int countAdminUserProducts(AdminSearchUserProductDTO search);
    
    // 페이징+검색 결과
    List<AdminUserProductDTO> searchAdminUserProducts(Map<String,Object> params);
    
	// 페이지네이션 전 - 기존
	List<AdminUserProductDTO> selectUserProducts();
	
	// 단일상품 싱세페이지 조회
	AdminUserProductDTO selectByPrdNum(@Param("prdNum") String prdNum);
	
	// 숨김 업데이트 - 체크박스, 단일 상세
//    void updateHiddenType(@Param("prdNums") List<Integer> prdNums, @Param("hidden") String hidden);
    void updateHiddenType(Map<String, Object> map);
    
    void updateUserProduct(AdminUserProductDTO aupDTO);
   
	
}
