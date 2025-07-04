package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 결제 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentsDTO {
	
	private String patmentId; /* 결제자 */
	private int amount; /* 결제금액 */
	private String method; /* 결제수단 */
	private Date patmentDate; /* 결제일 */
	private String tradeId; /* 거래자 */

} //class
