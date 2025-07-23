package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.ChatmessageDTO;
import kr.co.sist.DTO.ChatroomDTO;
import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.ChatDAO;

@Service
public class ChatService {
	
	@Autowired
    private ChatDAO chatDAO;
	
	public boolean checkChatRoomExist(String prdNum, String sellerNum, String buyerNum) {
		// 채팅방 객체 생성
        ChatroomDTO chatRoom = new ChatroomDTO();
        chatRoom.setPrdNum(prdNum);
        chatRoom.setSellerNum(sellerNum);
        chatRoom.setBuyerNum(buyerNum);
        
        return chatDAO.checkChatRoomExist(chatRoom);
	}

    // 채팅방 생성
    public void createChatRoom(String prdNum, String sellerNum, String buyerNum) {
    	
        // 채팅방 객체 생성
        ChatroomDTO chatRoom = new ChatroomDTO();
        chatRoom.setPrdNum(prdNum);
        chatRoom.setSellerNum(sellerNum);
        chatRoom.setBuyerNum(buyerNum);

        // 채팅방 DB에 저장
        chatDAO.createChatRoom(chatRoom);
    }
    
    //채팅방 리스트 가져오기
    public List<ChatroomDTO> getChatRoomsByUser (String userNum) {
    	return chatDAO.getChatRoomsByUser(userNum);
    }
    
    //마지막 채팅 가져오기
    public ChatmessageDTO getLastMessage (int chatroomNum) {
    	return chatDAO.getLastMessage(chatroomNum);
    }
    
    //다른 사용자 정보 가져오기
    public UserDTO selectUserByUserNum (String userNum) {
    	return chatDAO.selectUserByUserNum(userNum);
    }
    
    //다른 사용자 userNum얻기
    public String getOtherUser(String userNum,int chatroomNum) {
    	return chatDAO.getOtherUser(userNum,chatroomNum);
    }
    
    public void addMessage (ChatmessageDTO message){
    	chatDAO.insertMessage(message);
    }
    
}
