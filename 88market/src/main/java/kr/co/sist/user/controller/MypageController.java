package kr.co.sist.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.sist.DTO.BankDTO;
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
        
        // 안전거래 추가
        int safeCount = productService.getSafeByLoginId(loginId);
        model.addAttribute("safeCount", safeCount);
        
        List<ProductDTO> myProducts = productService.getProductsByLoginId(loginId);

        // 상품 이미지
        for (ProductDTO item : myProducts) {
        	item.setImgDTO(productService.selectImageByNum(item.getImgNum()));
        }// end for

        // 좋아 상품 조회도, 이미지도 모두 담겨있다
        model.addAttribute("myProducts", myProducts);
        model.addAttribute("totalCount", myProducts.size());

        
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
        
        // 내 정보 조회
        UserDTO userDTO = productService.getUserByLoginId(loginId);
        model.addAttribute("userDTO", userDTO);
        
/*        List<BankDTO> bankList = userService.getBankList(userNum);
        model.addAttribute("bankList", bankList);*/
        
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
    /*
    * AJAX 호출용: 내 정보(JSON) 업데이트 처리
    */
   @ResponseBody
   @PutMapping("/mypage/update-info")
   public Map<String, Object> updateInfo(
           @RequestBody UserDTO dto,
           HttpServletRequest request
   ) {
       Map<String, Object> result = new HashMap<>();

       // 1) JWT 토큰 검증 (기존 mypage()와 동일)
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
           result.put("success", false);
           result.put("msg", "로그인이 필요합니다.");
           return result;
       }

       // 2) userNum 설정 (절대 클라이언트가 userNum을 조작하면 안 됩니다)
       String userNum = jwtService.getClaims(token).get("userNum", String.class);
       dto.setUserNum(userNum);

       // 3) Service 호출
       boolean ok = userService.updateUserInfo(dto);
       result.put("success", ok);
       if (!ok) {
           result.put("msg", "서버 오류로 정보를 저장하지 못했습니다.");
       }
       return result;
   }

   /**
    * (선택) 순수 HTML fragment만 불러오기 위한 엔드포인트
    */
   @GetMapping("/mypage/info-fragment")
   public String infoFragment() {
       // templates/fragments/user/mypageList/info.html 의 th:fragment="info" 부분만 리턴
       return "fragments/user/mypageList/info :: info";
   }
   /*
   @GetMapping("/mypage/bank-fragment")
   public String bankFragment(HttpServletRequest request, Model model) {
       String token = jwtService.resolveToken(request);
       if (token == null || !jwtService.validateToken(token)) {
           // 비로그인 시 빈 리스트 또는 오류 처리
           model.addAttribute("bankList", List.of());
       } else {
           String userNum = jwtService.getClaims(token).get("userNum", String.class);
           List<BankDTO> bankList = userService.getBankList(userNum);
           model.addAttribute("bankList", bankList);
       }
       // templates/fragments/user/mypageList/bank.html 의 th:fragment="bank" 부분만 반환
       return "fragments/user/mypageList/bank :: bank";
   }

   @ResponseBody
   @PostMapping("/mypage/bank")
   public Map<String,Object> addBank(
           @RequestBody BankDTO dto,
           HttpServletRequest request
   ) {
       Map<String,Object> result = new HashMap<>();
       String token = jwtService.resolveToken(request);
       if (token == null || !jwtService.validateToken(token)) {
           result.put("success", false);
           result.put("msg", "로그인이 필요합니다.");
           return result;
       }
       String userNum = jwtService.getClaims(token).get("userNum", String.class);
       dto.setUserNum(userNum);

       boolean ok = userService.addBank(dto);
       result.put("success", ok);
       if (!ok) result.put("msg", "서버 오류로 계좌를 저장하지 못했습니다.");
       return result;
   }

   @ResponseBody
   @PostMapping("/mypage/bank/delete")
   public Map<String,Object> deleteBank(
           @RequestParam("bankId") String bankId,
           HttpServletRequest request
   ) {
       Map<String,Object> result = new HashMap<>();
       String token = jwtService.resolveToken(request);
       if (token == null || !jwtService.validateToken(token)) {
           result.put("success", false);
           result.put("msg", "로그인이 필요합니다.");
           return result;
       }

       boolean ok = userService.removeBank(bankId);
       result.put("success", ok);
       if (!ok) result.put("msg", "서버 오류로 계좌를 삭제하지 못했습니다.");
       return result;
   }
	
	*/
}
