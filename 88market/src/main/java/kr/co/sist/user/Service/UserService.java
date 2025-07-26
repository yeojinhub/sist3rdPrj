package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.UserDAO;

@Service
public class UserService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private ReportService reportService;

    /** 사용자번호로 조회: UserDTO 에 totalReport 까지 세팅 */
    public UserDTO findByUserNum(String userNum) {
        UserDTO dto = userDao.selectUserInfoByUserNum(userNum);
        if (dto != null) {
            int cnt = reportService.countByUserNum(userNum);
            dto.setTotalReport(cnt);
        }
        return dto;
    }

    /** 이메일로 조회: UserDTO 에 totalReport 까지 세팅 */
    public UserDTO findByEmail(String email) {
        UserDTO dto = userDao.selectUserInfoByEmail(email);
        if (dto != null) {
            int cnt = reportService.countByUserNum(dto.getUserNum());
            dto.setTotalReport(cnt);
        }
        return dto;
    }


    public Integer getTotalReport(String userNum) {
        Integer cnt = userDao.selectTotalReportByUserNum(userNum);
        return (cnt != null ? cnt : 0);
    }
    
    
    
    
}
