package kr.co.sist.admin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.admin.DAO.AdminNoticeDAO;
import kr.co.sist.admin.util.NoticeSearchDTO;
import kr.co.sist.admin.util.Pagination;

@Service
public class AdminNoticeService {

    @Autowired
    private AdminNoticeDAO adminNoticeDAO;

    public List<NoticeDTO> getNoticeList(Pagination pagination, NoticeSearchDTO nsDTO) {
        // 전체 개수 조회 (검색 조건 추가)
        int totalCount = adminNoticeDAO.selectTotalCount(nsDTO);
        pagination.setTotalCount(totalCount);
        
        // 페이지 데이터 조회 (검색 조건 추가)
        return adminNoticeDAO.selectAllNotice(pagination, nsDTO);
    }

    public void insertNotice(NoticeDTO noticeDTO) {
        // 관리자 계정 미구현으로 하드코딩
        noticeDTO.setAdmNum(1);
        noticeDTO.setName("강태일");

        adminNoticeDAO.insertNotice(noticeDTO);
    }

    public NoticeDTO getNoticeDetail(int notNum) {
        return adminNoticeDAO.selectNoticeDetail(notNum);
    }

    public void updateNotice(NoticeDTO noticeDTO) {
        adminNoticeDAO.updateNotice(noticeDTO);
    }

    public void deleteNotices(List<Integer> notNums) {
        adminNoticeDAO.deleteNotices(notNums);
    }
}
