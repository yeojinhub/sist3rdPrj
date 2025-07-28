package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.InquiryDTO;

@Mapper
public interface AdminHomeDAO {
    
    /**
     * 최근 30일간 일별 가입자 수 조회
     */
    List<Map<String, Object>> selectMonthlyUserRegistrations();
    
    /**
     * 최근 30일간 일별 거래 수 조회
     */
    List<Map<String, Object>> selectMonthlyTradeData();
    
    /**
     * 총 상품 수 조회
     */
    int selectTotalProductCount();
    
    /**
     * 기업 상품 수 조회
     */
    int selectCompanyProductCount();
    
    /**
     * 사용자 등록 상품 수 조회
     */
    int selectUserProductCount();
    
    /**
     * 대기 중인 문의사항 조회 (최대 10개)
     */
    List<InquiryDTO> selectPendingInquiries();
}
