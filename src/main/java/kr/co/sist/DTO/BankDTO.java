package kr.co.sist.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 계좌 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BankDTO {

	private String userNum; /* 사용자번호 */
	
	private String name; /* 계좌예금주 */
	private String bankName; /* 계좌은행명 */
	private String bankNum; /* 계좌번호 */
	private String bankType;  /* 대표계좌여부(Y,N) */

} //class
