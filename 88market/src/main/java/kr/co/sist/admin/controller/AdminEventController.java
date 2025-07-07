package kr.co.sist.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminEventController {

    @GetMapping("/event/add")
    public String eventAdd() {
        return "admin/event/eventAdd";
    }
    
    @PostMapping("/event/add")
    public String eventAddProcess() {
    	return "admin/event/eventAdd";
    }
	
}
