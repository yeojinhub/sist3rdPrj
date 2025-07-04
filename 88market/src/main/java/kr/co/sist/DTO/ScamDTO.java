package kr.co.sist.DTO;

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
	  private String inputDate; /* 사기조회일 */

} //class
