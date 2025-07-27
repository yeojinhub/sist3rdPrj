package kr.co.sist.admin.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import kr.co.sist.DTO.ChatmessageDTO;
import kr.co.sist.DTO.ChatroomDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.admin.Service.AdminChatService;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;
import kr.co.sist.user.Service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminChatController {

	@Autowired
	private ProductService ps;
	
    @Autowired
    private AdminChatService acs;

    @GetMapping("/chat")
    public String chatList(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            Model model) {
    	// 페이지네이션 객체 생성
        Pagination pagination = new Pagination();
        pagination.setPageNum(page);

        // 검색 조건 객체 생성
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchType(searchType);
        searchDTO.setKeyword(keyword);
        
        List<ChatroomDTO> chatList = acs.getAllChat(pagination, searchDTO);
        List<ProductDTO> chatListPrd = new ArrayList<ProductDTO>();
        List<UserDTO> buyerUserList = new ArrayList<UserDTO>();
        List<UserDTO> sellerUserList = new ArrayList<UserDTO>();
        List<String> buyerNameList = new ArrayList<String>();
        List<String> sellerNameList = new ArrayList<String>();
        for (ChatroomDTO cr : chatList) {
        	chatListPrd.add(ps.selectProductByNum(cr.getPrdNum()));
        	buyerUserList.add(ps.getUser(cr.getBuyerNum()));
        	sellerUserList.add(ps.getUser(cr.getSellerNum()));
        }
        for (UserDTO ut : buyerUserList) {
        	buyerNameList.add(ut.getNickname());
        }
        for (UserDTO ut : sellerUserList) {
        	sellerNameList.add(ut.getNickname());
        }
        model.addAttribute("chatList",chatList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("buyerNameList",buyerNameList);
        model.addAttribute("sellerNameList",sellerNameList);
        model.addAttribute("chatListPrd",chatListPrd);
        
        return "admin/chat/chatList"; 
    }
    
    @PostMapping("/chat/delete")
    @ResponseBody
    public ResponseEntity<?> deleteChatrooms(@RequestBody Map<String, List<Integer>> payload) {
        List<Integer> chatroomNums = payload.get("chatroomNums");
        try {
            acs.deleteChatrooms(chatroomNums);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }
    
    @GetMapping("/chat/download")
    public ResponseEntity<Resource> downloadChatLog(@RequestParam int chatroomNum) throws IOException {
        List<ChatmessageDTO> messages = acs.getMessagesByChatroomNum(chatroomNum);
        StringBuilder sb = new StringBuilder();

        for (ChatmessageDTO msg : messages) {
            String nickname = acs.getNicknameByUserNum(msg.getUserNum()); // 사용자 번호로 닉네임 조회
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(msg.getCreateDate());
            sb.append(nickname)
              .append(": ")
              .append(msg.getContent())
              .append(" (")
              .append(formattedDate)
              .append(")\n");
        }

        String fileName = "chatroom_" + chatroomNum + "_log.txt";
        ByteArrayResource resource = new ByteArrayResource(sb.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(resource.contentLength())
                .body((Resource) resource);  // 명시적인 타입 캐스팅
    }




}
