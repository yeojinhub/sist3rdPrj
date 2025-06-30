package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.*;
import kr.co.sist.user.DAO.NoticeDAO;

@Service
public class NoticeService {
    @Autowired
    private NoticeDAO noticeDAO;

    public List<NoticeDTO> selectNoticeList() {
        return noticeDAO.selectNoticeList();
    }
}
