package kr.co.sist.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.EventDTO;
import kr.co.sist.DTO.ImageDTO;

@Mapper
public interface AdminEventDAO {
	public List<EventDTO> selectAllEvent();
	public List<ImageDTO> selectAllImage();
}
