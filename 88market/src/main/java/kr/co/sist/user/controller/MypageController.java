package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.TradesDTO;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.BuyService;
import kr.co.sist.user.Service.JwtService;
import kr.co.sist.user.Service.ProductService;
import kr.co.sist.user.Service.UserService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MypageController {
	// 민경
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private BuyService buyService;

    @GetMapping("/mypage")
    public String mypage(
            @RequestParam(name="tab", defaultValue="mypageMain") String tab,
            HttpServletRequest request,
            Model model
    ) {
        // 쿠키 사용해서 인증 하기... 이걸 못했네 ㅋㅋㅋㅋㅋ아 ...
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }
        if (token == null || !jwtService.validateToken(token)) {
            return "redirect:/login";
        }

        String userNum = jwtService.getClaims(token).get("userNum", String.class);
        UserDTO userInfo = userService.findByUserNum(userNum);
        if (userInfo == null) {
          return "redirect:/login";
        }
        
        model.addAttribute("userInfo", userInfo);
        
        // 내 상품 조회
        String loginId = userInfo.getEmail();
        List<ProductDTO> myProducts = productService.getProductsByLoginId(loginId);
        model.addAttribute("myProducts", myProducts);
        model.addAttribute("totalCount", myProducts.size());

        
        // 상품 이미지

        
        // 내 구매 내역 조회
        List<TradesDTO> purchaseHistory = buyService.getPurchaseHistory(userNum);
        model.addAttribute("purchaseHistory", purchaseHistory);
        
        // 내 찜 목록 조회
        List<ProductDTO> wishlist = productService.getWishlist(userNum);
        model.addAttribute("wishlist", wishlist);
        
        // fragment
        List<String> validTabs = List.of( "mypageMain","sales","purchase","wishlist","info","bank","address","review","withdraw" );
        if (!validTabs.contains(tab)) {
            tab = "mypageMain";
        }
        model.addAttribute("tab", tab);

        return "user/account/mypage";
    }// 인증 끝

    @PostMapping("/mypage/wishlist/remove")
    public String removeFromWishlist(@RequestParam("productId") String productId,
                                     HttpServletRequest request,
                                     Model model) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }
        if (token == null || !jwtService.validateToken(token)) {
            return "redirect:/login";
        }

        String userNum = jwtService.getClaims(token).get("userNum", String.class);

        // 상품 삭제 로직
        productService.removeFromWishlist(userNum, productId);

        // 찜 목록 다시 가져오기
        List<ProductDTO> wishlist = productService.getWishlist(userNum);
        model.addAttribute("wishlist", wishlist);
        
        return "redirect:/mypage?tab=wishlist";
    }
	
	
}
