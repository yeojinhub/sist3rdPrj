package kr.co.sist.user.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.EventDTO;
import kr.co.sist.user.DAO.EventDAO;

@Service
public class EventService {

	@Autowired
	private EventDAO eDAO;
	
	public Map<String, List<EventDTO>> searchAllEvent() {
		List<EventDTO> eventList = eDAO.selectAllEvent();
		
		for (EventDTO item : eventList) {
			item.setIDTO(eDAO.selectImageWithImgNum(item.getImgNum()));
		}// end for
		
		// 진행중, 종료, 당첨자발표로 구분해서 맵에 담고 반환
		List<EventDTO> ingEvent = new ArrayList<EventDTO>();
		List<EventDTO> endEvent = new ArrayList<EventDTO>();
		List<EventDTO> winnerEvent = new ArrayList<EventDTO>();

		
		Date now = new Date();
		for (EventDTO item : eventList) {
			
			if (item.getEndDate().after(now)) {
				ingEvent.add(item);
			} else if (item.getEndDate().before(now)) {
				endEvent.add(item);
			}// end if-else
			
			if (item.getEvtType() == 2) {
				winnerEvent.add(item);
			}// end if
		}// end for
		
		
		Map<String, List<EventDTO>> eventMap = new HashMap<String, List<EventDTO>>();
		eventMap.put("ing", ingEvent);
		eventMap.put("end", endEvent);
		eventMap.put("winner", winnerEvent);
		
		return eventMap;
	}// searchAllEvent
	
	public EventDTO searchOneEvent(int evtNum) {
		return eDAO.selectOneEvent(evtNum);
	}// end searchOneEvent
	
}// class
