package kr.co.sist.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        // 4. 모델에 추가
        model.addAttribute("product", product);
        model.addAttribute("image", image);
        model.addAttribute("addrList", addrList);

        return "user/product/buy";
    }// buy

    @PostMapping("/buy/payment")
    @ResponseBody
    public ResponseEntity<?> payment(@RequestParam("formData") Map<String, String> formData) {
        System.out.println(formData);
        return ResponseEntity.ok(Map.of("success", true));
    }
	
}
