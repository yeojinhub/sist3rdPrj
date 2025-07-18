package kr.co.sist.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> payment(@RequestBody Map<String, Object> data, @AuthenticationPrincipal UserDetails user) {
        
    	try {
    		buyService.addTradeHistroy(data, user);
    		return ResponseEntity.ok(Map.of("result", true));
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","결제에 실패하였습니다. 다시 시도 해주세요."));
    		
    	}//end try-catch
    	
    }// payment
    
    @PostMapping("/sameUser")
    @ResponseBody
    public ResponseEntity<?> sameUser(@RequestBody Map<String, String> map, @AuthenticationPrincipal UserDetails user) {
    	
    	if (map.get("userNum").equals(user.getUsername())) {
    		return ResponseEntity.ok(Map.of("result",true));
    	} else {
    		return ResponseEntity.ok(Map.of("result",false));
    	}// end if-else
    	
    }// sameUser
	
}// class
