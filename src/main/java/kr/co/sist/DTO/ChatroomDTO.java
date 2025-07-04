package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 채팅방 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatroomDTO {

	private int chatroomNum; /* 채팅방번호*/
	private int sellerNum; /* 판매자회원번호 */
	private int buyerNum; /* 구매자회원번호 */
	
	private Date createDate; /* 채팅방생성일시 */
	private Date updateDate; /* 채팅방수정일 */
	private String readStatus; /* 채팅방상태(Y,N)*/
	
	private String prdNum; /* 상품번호 */
	private String userNum;/* 사용자번호 */

} //class
