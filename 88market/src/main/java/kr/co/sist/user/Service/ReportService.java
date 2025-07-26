package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.ReportDTO;
import kr.co.sist.user.DAO.ReportDAO;
@Service
public class ReportService {
	
    @Autowired
    private ReportDAO reportDao;

    /** 특정 사용자가 받은 신고 내역 전체 조회 */
    public List<ReportDTO> getReportsByUserNum(String userNum) {
        return reportDao.selectReportsByUserNum(userNum);
    }

    /** 특정 사용자가 받은 신고 건수 조회 */
    public int countByUserNum(String userNum) {
        return reportDao.countByUserNum(userNum);
    }

}
