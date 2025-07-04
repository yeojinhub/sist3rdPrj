package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 문의사항 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor 
@Getter
@Setter
@ToString
public class InquiryDTO {

	private int inqNum; /* 문의사항번호 */
	private String title; /* 문의사항제목 */
	private String content; /* 문의사항내용 */
	private String name; /* 문의사항작성자 */
	private Date inputDate; /* 문의사항작성일 */
	private String statusType; /* 문의사항상태(대기,답변완료) */
	
	private int userNum; /* 사용자번호 */
	private String admNum; /* 관리자사번 */
	private int imgNum; /* 이미지번호 */

} //class
