package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 후기 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDTO {

	private int revNum; /* 후기번호 */
	private String content; /* 후기내용 */
	private String name; /* 후기작성자 */
	private Date input_date; /* 후기작성일 */
	private int reportCnt; /* 누적신고수 */
	private String hiddenType; /* 후기숨김여부(Y,N) */
	private String keyword; /* 후기선택키워드 */
	
	private String prdNum; /* 상품번호 */

} //class
