package kr.co.sist.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.ChatmessageDTO;
import kr.co.sist.DTO.ChatroomDTO;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Mapper
public interface AdminChatDAO {
	public List<ChatroomDTO> selectAllChat(
	        @Param("pagination") Pagination pagination, 
	        @Param("nsDTO") SearchDTO nsDTO
	);
	public int selectTotalCount(@Param("nsDTO") SearchDTO nsDTO);
	void deleteChatrooms(List<Integer> chatroomNums);
	List<ChatmessageDTO> getMessagesByChatRoomNum(int chatroomNum);
	String selectNicknameByUserNum(String userNum);
}
