package kr.co.sist.admin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.FaqDTO;
import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.admin.DAO.AdminFaqDAO;
import kr.co.sist.admin.util.FaqSearchDTO;
import kr.co.sist.admin.util.Pagination;

@Service
public class AdminFaqService {

    @Autowired
    private AdminFaqDAO adminFaqDAO;

    public List<FaqDTO> getFaqList(Pagination pagination, FaqSearchDTO fsDTO) {
        // 전체 개수 조회 (검색 조건 추가)
        int totalCount = adminFaqDAO.selectTotalCount(fsDTO);
        pagination.setTotalCount(totalCount);
        
        // 페이지 데이터 조회 (검색 조건 추가)
        return adminFaqDAO.selectAllNotice(pagination, fsDTO);
    }
    
    public void deleteFaqs(List<Integer> faqNums) {
        adminFaqDAO.deleteFaqs(faqNums);
    }
    
    public void insertFaq(FaqDTO faqDTO) {
        // 관리자 계정 미구현으로 하드코딩
    	faqDTO.setAdmNum(1);
    	faqDTO.setName("강태일");

        adminFaqDAO.insertFaq(faqDTO);
    }
    
    public FaqDTO getFaqDetail(int faqNum) {
        return adminFaqDAO.selectFaqDetail(faqNum);
    }

    public void updateFaq(FaqDTO faqDTO) {
        adminFaqDAO.updateFaq(faqDTO);
    }
}
