package kr.co.sist.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sist.DTO.EventDTO;
import kr.co.sist.user.Service.EventService;

@Controller
public class EventController {

	@Autowired
	private EventService es;
	
	@GetMapping("/event")
	public String event(Model model) {
		
		Map<String, List<EventDTO>> map = es.searchAllEvent();
		
		model.addAttribute("ingEventList",map.get("ing"));
		model.addAttribute("endEventList",map.get("end"));
		model.addAttribute("winnerEventList",map.get("winner"));

		return "user/event/event";
	}
	
	@GetMapping("/event/{id}")
	public String eventDetail(Model model,@PathVariable int id) {
		
		model.addAttribute("eventDTO", es.searchOneEvent(id));
		
		return "user/event/eventDetail";
	}
	
}
