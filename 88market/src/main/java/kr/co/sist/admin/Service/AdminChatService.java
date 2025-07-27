package kr.co.sist.admin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.ChatmessageDTO;
import kr.co.sist.DTO.ChatroomDTO;
import kr.co.sist.admin.DAO.AdminChatDAO;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Service
public class AdminChatService {

	@Autowired
	private AdminChatDAO acd;
	
	public List<ChatroomDTO> getAllChat(Pagination pagination, SearchDTO nsDTO) {
        // 전체 개수 조회 (검색 조건 추가)
        int totalCount = acd.selectTotalCount(nsDTO);
        pagination.setTotalCount(totalCount);
        
        // 페이지 데이터 조회 (검색 조건 추가)
        return acd.selectAllChat(pagination, nsDTO);
    }
	
	public void deleteChatrooms(List<Integer> chatroomNums) {
	    acd.deleteChatrooms(chatroomNums);
	}
	
	public List<ChatmessageDTO> getMessagesByChatroomNum(int chatroomNum) {
	    return acd.getMessagesByChatRoomNum(chatroomNum); // create_date로 정렬된 채팅
	}

	public String getNicknameByUserNum(String userNum) {
	    return acd.selectNicknameByUserNum(userNum);
	}

}
