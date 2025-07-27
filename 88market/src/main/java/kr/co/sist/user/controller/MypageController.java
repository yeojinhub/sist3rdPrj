package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.PurchaseDTO;
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

        // 상품 이미지
        for (ProductDTO item : myProducts) {
        	item.setImgDTO(productService.selectImageByNum(item.getImgNum()));
        }// end for

        // 좋아 상품 조회도, 이미지도 모두 담겨있다
        model.addAttribute("myProducts", myProducts);
        model.addAttribute("totalCount", myProducts.size());
        
        // 내 구매 내역 조회
        List<PurchaseDTO> purchaseHistory = buyService.getPurchaseHistory(userNum);
        for (PurchaseDTO item : purchaseHistory) {
        	item.getProductDTO().setImgDTO(productService.selectImageByNum(item.getProductDTO().getImgNum()));
        }
        model.addAttribute("purchaseHistory", purchaseHistory);
        
        // 내 찜 목록 조회
        List<ProductDTO> wishlist = productService.getWishlist(userNum);
        for (ProductDTO item : wishlist) {
        	item.setImgDTO(productService.selectImageByNum(item.getImgNum()));
        }
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
	
    @PostMapping("/mypage/product/delete")
    @ResponseBody
    public ResponseEntity<String> deleteProduct(@RequestParam("prdNum") String prdNum,
                                                @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            // 서비스 메서드 호출하여 상품 삭제
            productService.deleteProduct(prdNum);  
            return ResponseEntity.ok("상품 삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 삭제 실패: " + e.getMessage());
        }
    }
}
