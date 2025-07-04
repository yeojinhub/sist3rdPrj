package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.*;

@Mapper
public interface NoticeDAO {
	List<NoticeDTO> selectNoticeList();
}
