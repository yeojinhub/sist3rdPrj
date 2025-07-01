package kr.co.sist.admin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.admin.DAO.AdminNoticeDAO;

@Service
public class AdminNoticeService {

	@Autowired
	private AdminNoticeDAO and;
	
	public List<NoticeDTO> searchAllNotice() {
		return and.selectAllNotice();
	}
	
}
