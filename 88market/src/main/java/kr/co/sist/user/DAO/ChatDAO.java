package kr.co.sist.user.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.sist.DTO.ChatmessageDTO;
import kr.co.sist.DTO.ChatroomDTO;
import kr.co.sist.DTO.UserDTO;

@Repository
public class ChatDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public void createChatRoom(ChatroomDTO chatroom) {
		sqlSession.insert("ChatMapper.createChatRoom",chatroom);
	}
	
	public List<ChatroomDTO> getChatRoomsByUser(String userNum){
		return sqlSession.selectList("ChatMapper.getChatRoomsByUser",userNum);
	}
	
	public boolean checkChatRoomExist(ChatroomDTO chatroom) {
		Integer result = sqlSession.selectOne("ChatMapper.checkChatRoomExist",chatroom);
		return result != null && result > 0;
	}
	
    public List<Map<String,Object>> getChatRoomSummaryByUserNum(String userNum){
        return sqlSession.selectList("ChatMapper.selectChatRoomsByUserNum", userNum);
    }

    public UserDTO selectUserByUserNum(String userNum) {
        return sqlSession.selectOne("ChatMapper.selectUserByUserNum", userNum);
    }
    
    public ChatmessageDTO getLastMessage(int chatroomNum) {
    	return sqlSession.selectOne("ChatMapper.getLastMessage",chatroomNum);
    }
    
    public String getOtherUser(String userNum, int chatroomNum) {
    	Map<String, Object> param = new HashMap<>();
    	param.put("userNum", userNum);
    	param.put("chatroomNum", chatroomNum);
    	return sqlSession.selectOne("ChatMapper.getOtherUser",param);
    }
    
    public void insertMessage(ChatmessageDTO message) {
    	sqlSession.insert("ChatMapper.insertMessage",message);
    }
}
