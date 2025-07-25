package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.EventDTO;
import kr.co.sist.DTO.ImageDTO;

@Mapper
public interface EventDAO {

	public List<EventDTO> selectAllEvent();
	public ImageDTO selectImageWithImgNum(int ImgNum);
	public EventDTO selectOneEvent(int evtNum);
	
}
