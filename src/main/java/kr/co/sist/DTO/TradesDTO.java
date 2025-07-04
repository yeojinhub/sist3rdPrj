package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 거래 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TradesDTO {

	private String tradeId; /* 거래자ID */
	private String buyerId; /* 구매자ID */
	private String tradeStatus; /* 거래상태 */
	private Date tradeDate; /* 거래일 */
	
	private String prdNum; /* 상품번호 */

} //class
