package kr.co.sist.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.NoticeDTO;

@Mapper
public interface AdminNoticeDAO {
	public List<NoticeDTO> selectAllNotice();
}
