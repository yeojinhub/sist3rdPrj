package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BuyController {

	@GetMapping("/buy/{id}")
    public String buy(@PathVariable("id") int prdNum) {
		
		System.out.println(prdNum);
		
        return "user/product/buy";
    }
	
}
