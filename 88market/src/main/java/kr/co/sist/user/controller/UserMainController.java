package kr.co.sist.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.EventDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.Service.CategoryService;
import kr.co.sist.user.Service.EventService;
import kr.co.sist.user.Service.ProductService;
import kr.co.sist.user.Service.SearchItemService;

@Controller
public class UserMainController {
	
    @Autowired
    private SearchItemService serchService;
    
    @Autowired
    private CategoryService categoryService;

	@Autowired
	private EventService eventService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String main(Model model, @AuthenticationPrincipal UserDetails currentUser) {

		// 이벤트 이미지 가져오기
		List<EventDTO> eventList = eventService.searchMainEvent();
		model.addAttribute("eventList", eventList);

		//String email = auth.getName();  // 토큰의 sub 값
        //model.addAttribute("userEmail", email);
		
		/* 메인 - 찜 많은 것 */
	    List<ProductDTO> topLike = serchService.getTopLikeItems(15);
	    model.addAttribute("topLike", topLike);
	    List<ImageDTO> topLikeImages = new ArrayList<ImageDTO>();
	    for(ProductDTO P : topLike) {
        	topLikeImages.add(productService.selectImageByNum(P.getImgNum()));
	    }
	    model.addAttribute("topLikeImages",topLikeImages);
	    //찜
	    List<Boolean> topLikeWish = new ArrayList<Boolean>();
	    if (currentUser != null) {
            String currentUserNum = currentUser.getUsername();
            for(ProductDTO P : topLike) {
            	boolean liked = productService.checkFavorite(currentUserNum, P.getPrdNum());
            	topLikeWish.add(liked);	
            }
        }else {
            // 로그인 안 한 경우 false로 채움
            for (int i = 0; i < topLike.size(); i++) {
                topLikeWish.add(false);
            }
        }
	    model.addAttribute("topLikeWish",topLikeWish);
		
	    /* 메인 - 카테고리별 15개 랜덤 */
        CategoryDTO randomCat = categoryService.getRandomCategory();
        if (randomCat != null) {
            model.addAttribute("trendCategory", randomCat.getName());
            
            model.addAttribute("trendCatNum", randomCat.getCatNum());
            List<ProductDTO> trendItems = serchService.findByCategoryRandom(randomCat.getCatNum(), 15);
            model.addAttribute("trendItems", trendItems);
            List<ImageDTO> trendItemsImages = new ArrayList<ImageDTO>();
            for(ProductDTO P : trendItems) {
            	trendItemsImages.add(productService.selectImageByNum(P.getImgNum()));
            }
            model.addAttribute("trendItemsImages",trendItemsImages);
            List<Boolean> trendItemsWish = new ArrayList<Boolean>();
    	    if (currentUser != null) {
                String currentUserNum = currentUser.getUsername();
                for(ProductDTO P : trendItems) {
                	boolean liked = productService.checkFavorite(currentUserNum, P.getPrdNum());
                	trendItemsWish.add(liked);
                }
            }else {
                // 로그인 안 한 경우 false로 채움
                for (int i = 0; i < trendItems.size(); i++) {
                	trendItemsWish.add(false);
                }
            }
    	    model.addAttribute("trendItemsWish",trendItemsWish);
        }
	    
		/* 메인 - 조회수 많은 것 */
		List<ProductDTO> topView=serchService.getTopViewItems(15);
		model.addAttribute("topView",topView);
		List<ImageDTO> topViewImages=new ArrayList<ImageDTO>();
		for(ProductDTO P : topView) {
			topViewImages.add(productService.selectImageByNum(P.getImgNum()));
		}
		model.addAttribute("topViewImages",topViewImages);
		List<Boolean> topViewWish = new ArrayList<Boolean>();
	    if (currentUser != null) {
            String currentUserNum = currentUser.getUsername();
            for(ProductDTO P : topView) {
            	boolean liked = productService.checkFavorite(currentUserNum, P.getPrdNum());
            	topViewWish.add(liked);
            }
        }else {
            // 로그인 안 한 경우 false로 채움
            for (int i = 0; i < topView.size(); i++) {
            	topViewWish.add(false);
            }
        }
	    model.addAttribute("topViewWish",topViewWish);
		
		
		
		return "index";
	}
	
	

}
