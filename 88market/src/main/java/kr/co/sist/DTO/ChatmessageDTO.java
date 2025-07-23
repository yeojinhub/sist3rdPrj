package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 채팅메세지 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatmessageDTO {

	private int msgId;
	private String content;
	private Date createDate;
	private String isRead;
	private String imgPath;
	private String userNum;
	
	private int chatroomNum; /* 채팅방번호 */
}
