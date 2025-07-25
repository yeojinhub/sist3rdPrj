package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 문의답변 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnswerDTO {

	private int inqNum; /* 문의사항번호 */
	private String title; /* 문의답변제목 */
	private String content; /* 문의답변내용 */
	private String name; /* 문의답변작성자 */
	private int admNum;
	
	private Date inputType; /* 문의답변작성일*/

} //class
