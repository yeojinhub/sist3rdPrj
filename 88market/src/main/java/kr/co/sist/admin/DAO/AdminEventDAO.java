package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.EventDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Mapper
public interface AdminEventDAO {
    
    // 기존 메소드들
	public int selectTotalCount(@Param("esDTO") SearchDTO esDTO);
    public List<EventDTO> selectAllEvent(
    		@Param("pagination") Pagination pagination, 
            @Param("esDTO") SearchDTO esDTO
    		);
    public ImageDTO selectAllImage(int imgNum);
    public void deleteEvents(List<Integer> evtNums);
    public Integer selectEventImageNum(Integer evtNum);
    public void deleteEventImage(int imgNum);
    public EventDTO selectEventDetail(@Param("evtNum") int evtNum);
    public void updateEvent(EventDTO eventDTO);
    public void updateEventImage(Map<String, Object> params);

    // 1. 이벤트 등록 후 생성된 evtNum 리턴
    public int insertEvent(EventDTO eventDTO);
    
    // 2. 이미지 정보 등록 후 imgNum 리턴
    public int insertImage(Map<String, Object> params);
    
    // 3. 이벤트 테이블의 imgNum 업데이트
    public int updateEventImgNum(Map<String, Object> params);
}
