package kr.co.sist.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.sist.admin.Service.AdminEventService;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;
import kr.co.sist.DTO.EventDTO;

@Controller
@RequestMapping("/admin")
public class AdminEventController {

    @Autowired
    private AdminEventService adminEventService;

    @GetMapping("/event/add")
    public String eventAdd() {
        return "admin/event/eventAdd";
    }
    
    @PostMapping("/event/add")
    public String eventAddProcess(
        EventDTO eventDTO,
        @RequestParam("thumbnailImage") MultipartFile thumbnailImage,
        @RequestParam("mainImage") MultipartFile mainImage,
        RedirectAttributes redirectAttributes
        ) {

        try {
            // 서비스에서 전체 프로세스 처리
            boolean result = adminEventService.addEvent(eventDTO, thumbnailImage, mainImage);
            
            if (result) {
                redirectAttributes.addFlashAttribute("message", "이벤트가 성공적으로 등록되었습니다.");
                return "redirect:/admin/event";
            } else {
                redirectAttributes.addFlashAttribute("error", "이벤트 등록에 실패했습니다.");
                return "redirect:/admin/event/add";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "시스템 오류가 발생했습니다.");
            return "redirect:/admin/event/add";
        }
    }
    
    @GetMapping("/event")
	public String event(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            Model model) {
		
        Pagination pagination = new Pagination();
        pagination.setPageNum(page);

        // 검색 조건 객체 생성
        SearchDTO esDTO = new SearchDTO();
        esDTO.setSearchType(searchType);
        esDTO.setKeyword(keyword);

        // 서비스 호출 (검색 조건 추가)
        model.addAttribute("eventList", adminEventService.getAllEvent(pagination, esDTO));
        model.addAttribute("pagination", pagination);

        // 검색 조건 유지를 위해 모델에 추가
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
		
		
		return "admin/event/eventList";
	}
}
