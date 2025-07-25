package kr.co.sist.admin.DAO;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.admin.util.SearchDTO;
import kr.co.sist.admin.util.Pagination;

@Mapper
public interface AdminNoticeDAO {
    public int selectTotalCount(@Param("nsDTO") SearchDTO nsDTO);
    public List<NoticeDTO> selectAllNotice(
        @Param("pagination") Pagination pagination, 
        @Param("nsDTO") SearchDTO nsDTO
    );
    public void insertNotice(NoticeDTO noticeDTO);
    public NoticeDTO selectNoticeDetail(@Param("notNum") int notNum);
    public void updateNotice(NoticeDTO noticeDTO);
    public void deleteNotices(List<Integer> notNums);
    public void updateNoticeHideOn(List<Integer> notNums);
    public void updateNoticeHideOff(List<Integer> notNums);
}
