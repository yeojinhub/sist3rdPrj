package kr.co.sist.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.ProductService;

@Controller
public class wishListController {

    @Autowired
    private ProductService productService;

    @GetMapping("/mypage/wishlist")
    public String getMypage(Model model, @RequestParam String userNum) {
        // userNum을 사용하여 찜목록을 가져온다
        List<ProductDTO> wishlist = productService.selectWishlistByUserNum(userNum);
        if (wishlist == null) {
            wishlist = new ArrayList<>();
        }
        

        // 디버깅: wishlist 상태 출력
        System.out.println("Wishlist: " + wishlist);
        System.out.println("Wishlist size: " + wishlist.size());
        model.addAttribute("wishlist", wishlist);

        return "user/mypage";
    }
}
