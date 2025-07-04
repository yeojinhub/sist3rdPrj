package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.SearchItemService;

@Controller
public class UserMainController {
	
    @Autowired
    private SearchItemService service;

	@GetMapping("/")
	public String main(Model model) {
		
		/* 메인 - 찜 많은 것 */
	    List<ProductDTO> topLike = service.getTopLikeItems(15);
	    model.addAttribute("topLike", topLike);
		
		/* 메인 - 조회수 많은 것 */
		List<ProductDTO> topView=service.getTopViewItems(15);
		model.addAttribute("topView",topView);
		return "index";
	}
	
	

}
