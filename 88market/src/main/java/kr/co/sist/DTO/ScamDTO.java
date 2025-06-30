package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사기조회 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScamDTO {
	
	  private int scamNum; /* 사기조회번호*/
	  private String inputValue; /* 사용자입력값 */
	  private String inputType; /* 입력타입(핸드폰번호, 계좌번호, 이메일) */
	  private Date inputDate; /* 사기조회일 */
	  
	  private String userNum; /* 사용자번호 */

} //class
