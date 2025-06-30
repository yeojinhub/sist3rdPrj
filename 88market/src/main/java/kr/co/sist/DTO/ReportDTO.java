package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 신고 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportDTO {

	private int repNum; /* 신고번호 */
	private String title; /* 신고제목 */
	private String content; /* 신고내용 */
	private String name; /* 신고자 */
	private Date inputDate; /* 신고일 */
	private int reportType; /* 신고타입(1,2) */
	
	private String userNum; /* 사용자번호 */

} //class
