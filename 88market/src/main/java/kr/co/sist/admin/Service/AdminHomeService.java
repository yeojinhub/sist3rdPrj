package kr.co.sist.admin.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.InquiryDTO;
import kr.co.sist.admin.DAO.AdminHomeDAO;

@Service
public class AdminHomeService {

    @Autowired
    private AdminHomeDAO adminHomeDAO;

    public List<Map<String, Object>> getMonthlyUserRegistrations() {
        try {
            List<Map<String, Object>> result = adminHomeDAO.selectMonthlyUserRegistrations();
            
            if (result != null && !result.isEmpty()) {
                System.out.println("DB에서 가입자 데이터 " + result.size() + "개 조회");
                return result;
            } else {
                System.out.println("DB 가입자 데이터가 없어서 샘플 데이터 생성");
                return createSampleUserData();
            }
        } catch (Exception e) {
            System.err.println("가입자 데이터 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return createSampleUserData();
        }
    }

    public List<Map<String, Object>> getMonthlyTradeData() {
        try {
            List<Map<String, Object>> result = adminHomeDAO.selectMonthlyTradeData();
            
            if (result != null && !result.isEmpty()) {
                System.out.println("DB에서 거래 데이터 " + result.size() + "개 조회");
                return result;
            } else {
                System.out.println("DB 거래 데이터가 없어서 샘플 데이터 생성");
                return createSampleTradeData();
            }
        } catch (Exception e) {
            System.err.println("거래 데이터 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return createSampleTradeData();
        }
    }

    public int getTotalProductCount() {
        try {
            return adminHomeDAO.selectTotalProductCount();
        } catch (Exception e) {
            System.err.println("총 상품 수 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int getCompanyProductCount() {
        try {
            return adminHomeDAO.selectCompanyProductCount();
        } catch (Exception e) {
            System.err.println("기업 상품 수 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int getUserProductCount() {
        try {
            return adminHomeDAO.selectUserProductCount();
        } catch (Exception e) {
            System.err.println("사용자 상품 수 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public List<InquiryDTO> getPendingInquiries() {
        try {
            List<InquiryDTO> result = adminHomeDAO.selectPendingInquiries();
            return result != null ? result : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("대기 중인 문의사항 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Map<String, Object>> createSampleUserData() {
        List<Map<String, Object>> sampleData = new ArrayList<>();
        String[] dates = {"01-22", "01-23", "01-24", "01-25", "01-26", "01-27", "01-28"};
        int[] counts = {9, 2, 9, 4, 10, 5, 5};
        
        for (int i = 0; i < dates.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("JOIN_DATE", dates[i]);
            data.put("JOIN_COUNT", counts[i]);
            sampleData.add(data);
        }
        return sampleData;
    }

    private List<Map<String, Object>> createSampleTradeData() {
        List<Map<String, Object>> sampleData = new ArrayList<>();
        String[] dates = {"01-22", "01-23", "01-24", "01-25", "01-26", "01-27", "01-28"};
        int[] userTrades = {3, 5, 4, 2, 1, 1, 5};
        int[] companyTrades = {2, 2, 1, 2, 2, 2, 1};
        
        for (int i = 0; i < dates.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("TRADE_DATE", dates[i]);
            data.put("USER_TRADES", userTrades[i]);
            data.put("COMPANY_TRADES", companyTrades[i]);
            sampleData.add(data);
        }
        return sampleData;
    }
}
