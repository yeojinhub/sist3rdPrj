package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.EventDTO;
import kr.co.sist.DTO.ImageDTO;

@Mapper
public interface AdminEventDAO {
    
    // 기존 메소드들
    List<EventDTO> selectAllEvent();
    List<ImageDTO> selectAllImage();
    
    // 1. 이벤트 등록 후 생성된 evtNum 리턴
    int insertEvent(EventDTO eventDTO);
    
    // 2. 이미지 정보 등록 후 imgNum 리턴
    int insertImage(Map<String, Object> params);
    
    // 3. 이벤트 테이블의 imgNum 업데이트
    int updateEventImgNum(Map<String, Object> params);
}
