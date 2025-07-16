package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.sist.DTO.AddressDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.BuyService;

@Controller
public class BuyController {

    @Autowired
    private BuyService buyService;

	@GetMapping("/buy/{id}")
    public String buy(
    		@PathVariable("id") String prdNum,
    		Model model,
    		@AuthenticationPrincipal UserDetails user
    		) {
        
        // 1. 해당 상품 정보 조회
        ProductDTO product = buyService.searchProduct(prdNum);

        // 2. 해당 상품 이미지 조회
        ImageDTO image = buyService.searchImage(product.getImgNum());
        
        // 3. 배송지 조회
        List<AddressDTO> addrList = buyService.searchAddressByUserNum(user.getUsername());
        
        for(AddressDTO item : addrList) {
        	System.out.println(item);
        }

        // 3. 모델에 추가
        model.addAttribute("product", product);
        model.addAttribute("image", image);

        return "user/product/buy";
    }
	
}
