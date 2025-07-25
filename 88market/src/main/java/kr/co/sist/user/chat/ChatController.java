package kr.co.sist.user.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sist.DTO.ChatmessageDTO;
import kr.co.sist.DTO.ChatroomDTO;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.ChatService;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 채팅방 오픈시 로그인 체크
    @GetMapping("/chat")
    public ResponseEntity<String> checkChat(
            @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String username = currentUser.getUsername();
        return ResponseEntity.ok(username);
    }
    
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatmessageDTO message) {
        int chatroomNum = message.getChatroomNum();  // 메시지에서 chatroomNum 가져오기

        // 메시지가 null인지 확인
        if (message == null) {
        	System.out.println("Error: Message is null");
        	return;
        }

        chatService.addMessage(message);
        
        // 클라이언트에게 해당 채팅방에 메시지 전송
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatroomNum, message);
    }
    
    //채팅방 생성
    @GetMapping("/chat/create")
    public ResponseEntity<String> createChatRoom(@RequestParam("prdNum") String prdNum,
                                                 @RequestParam("sellerNum") String sellerNum,
                                                 @AuthenticationPrincipal UserDetails currentUser) {
        
        // JWT 인증에서 user_num 추출
        String buyerNum = currentUser != null ? currentUser.getUsername() : null;
        
        if (buyerNum == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // 본인 물건에 대해 채팅 생성 요청 막기 (선택 사항)
        if (buyerNum.equals(sellerNum)) {
            return ResponseEntity.badRequest().body("자신의 상품에 대해 채팅을 시작할 수 없습니다.");
        }

        boolean exists = chatService.checkChatRoomExist(prdNum, sellerNum, buyerNum);

        if (exists) {
            return ResponseEntity.ok().body("Chatroom exists");
        } else {
            chatService.createChatRoom(prdNum, sellerNum, buyerNum);
            return ResponseEntity.ok().body("Chatroom created");
        }
    }
    
    @GetMapping("/chat/rooms")
    @ResponseBody
    public List<Map<String, Object>> getChatRooms(@AuthenticationPrincipal UserDetails currentUser) {
        String userNum = currentUser != null ? currentUser.getUsername() : null;

        // 채팅방 목록을 가져옵니다.
        List<ChatroomDTO> chatRooms = chatService.getChatRoomsByUser(userNum);

        List<Map<String, Object>> chatRoomDetails = new ArrayList<>();
        
        for (ChatroomDTO chatRoom : chatRooms) {
            // 상대방의 userNum을 가져옵니다.
            String opponentNum = chatService.getOtherUser(userNum,chatRoom.getChatroomNum());

            // 상대방의 프로필 정보를 가져옵니다.
            UserDTO opponentProfile = chatService.selectUserByUserNum(opponentNum);

            //프로필 이미지가 null일 경우
            String profileImage = (opponentProfile.getImage() != null) ? opponentProfile.getImage() : "/images/defaultProfile.png"; // 기본 이미지 경로
            
            // 마지막 메시지를 가져옵니다.
            ChatmessageDTO lastMessage = chatService.getLastMessage(chatRoom.getChatroomNum());

            //lastMessage가 null일 경우
            String lastMessageContent = (lastMessage != null) ? lastMessage.getContent() : "메시지가 없습니다";
            String lastMessageTime = (lastMessage != null) ? lastMessage.getCreateDate().toString() : "";
            
            
            // chatRoom에 추가 정보를 추가하여 Map에 담습니다.
            Map<String, Object> chatRoomInfo = new HashMap<>();
            chatRoomInfo.put("chatroomNum", chatRoom.getChatroomNum());
            chatRoomInfo.put("opponentNickname", opponentProfile.getNickname());
            chatRoomInfo.put("opponentImgPath", profileImage);
            chatRoomInfo.put("lastMessage", lastMessageContent);
            chatRoomInfo.put("lastMessageTime", lastMessageTime);
            
            chatRoomDetails.add(chatRoomInfo);
        }

        return chatRoomDetails;
    }
    
    @GetMapping("/chat/messages/{chatroomNum}")
    public ResponseEntity<List<ChatmessageDTO>> getMessagesByChatRoomNum(
            @PathVariable("chatroomNum") int chatroomNum,
            @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userNum = currentUser.getUsername();

        // 이 유저가 이 채팅방에 속해있는지 체크.
        // boolean isParticipant = chatService.isUserInChatRoom(chatroomNum, userNum);
        // if (!isParticipant) {
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // }

        List<ChatmessageDTO> messages = chatService.getMessagesByChatRoomNum(chatroomNum);
        return ResponseEntity.ok(messages);
    }

}
