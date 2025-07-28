package kr.co.sist.admin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.ReportDTO;
import kr.co.sist.admin.DAO.AdminReportDAO;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Service
public class AdminReportService {
	
	@Autowired
	private AdminReportDAO adminReportDAO;
	
	 // 신고 목록 가져오기
    public List<ReportDTO> getReportList(Pagination pagination, SearchDTO searchDTO) {
        // 전체 개수 조회
        int totalCount = adminReportDAO.selectTotalCount(searchDTO);
        
        // Pagination에 전체 개수 설정
        pagination.setTotalCount(totalCount);
        
        // 신고 목록 조회
        return adminReportDAO.selectAllReport(pagination, searchDTO);
    }
    
    public ReportDTO getReportByNum(int repNum) {
    	return adminReportDAO.selectReportByNum(repNum);
    }
    public void deleteReportByNum(int repNum) {
    	adminReportDAO.deleteReportByNum(repNum);
    }
}
