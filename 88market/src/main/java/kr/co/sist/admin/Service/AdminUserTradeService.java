package kr.co.sist.admin.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.PaymentsDTO;
import kr.co.sist.DTO.TradesDTO;
import kr.co.sist.admin.DAO.AdminUserTradeDAO;
import kr.co.sist.admin.util.AdminSearchUserTradeDTO;
import kr.co.sist.admin.util.UserProductPagination;

@Service
public class AdminUserTradeService {
    @Autowired
    private AdminUserTradeDAO dao;

    // 페이지 네이션 + 검색
    public UserProductPagination<TradesDTO> findByAdminSearch(AdminSearchUserTradeDTO dto) {
        int total = dao.countTrades(dto);

        UserProductPagination<TradesDTO> page = new UserProductPagination<>();
        page.setPageNum(dto.getPageNum());
        page.setPageSize(dto.getPageSize());
        page.setTotalCount(total);

        Map<String,Object> params = new HashMap<>();
        params.put("search", dto);
        params.put("startRow", page.getStartRow());
        params.put("endRow",   page.getEndRow());

        List<TradesDTO> list = dao.searchTrades(params);
        page.setItems(list);

        return page;
    }
    
    // 아이디 찾기
    public TradesDTO findById(Integer tradeId) {
        return dao.selectTradeById(tradeId);
    }
    // 거래 번호 찾기 -
    public PaymentsDTO findPaymentByTradeId(Integer tradeId) {
        return dao.selectPaymentByTradeId(tradeId);
    }
}
