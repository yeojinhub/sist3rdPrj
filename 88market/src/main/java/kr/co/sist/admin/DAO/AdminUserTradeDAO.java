package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.PaymentsDTO;
import kr.co.sist.DTO.TradesDTO;
import kr.co.sist.admin.util.AdminSearchUserTradeDTO;

@Mapper
public interface AdminUserTradeDAO {
	
	
    // 전체 건수
    int countTrades(AdminSearchUserTradeDTO search);
    // 페이징 + 검색
    List<TradesDTO> searchTrades(Map<String,Object> params);
    //거래 상세 조회
    TradesDTO selectTradeById(@Param("tradeId") Integer tradeId);
    PaymentsDTO selectPaymentByTradeId(Integer tradeId);
}
