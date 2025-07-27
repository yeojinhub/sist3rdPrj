package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.ReportDTO;

@Mapper
public interface ReportDAO {
    /**
     * 특정 사용자가 받은 모든 신고 내역 조회
     * @param userNum users.USER_NUM
     * @return List of ReportDTO
     */
    List<ReportDTO> selectReportsByUserNum(@Param("userNum") String userNum);

    /**
     * 특정 사용자가 받은 신고 수 집계
     * @param userNum users.USER_NUM
     * @return 신고 건수
     */
    int countByUserNum(@Param("userNum") String userNum);
}
