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
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TradesDTO {
	
	private Integer tradeId; /* 거래자ID - num */
	private String buyerId; /* 구매자ID - num */
	private String sellerId; /* 판매자ID - num */
	private String tradeStatus; /* 거래상태 */
	private Date tradeDate; /* 거래일 */
	private String deliveryNum; /* 배송지번호(택배거래 상품일 경우 배송지 입력을 받는데 해당 배송지에 대한 번호) */
	
	private String prdNum; /* 상품번호 */
	
	private String buyerEmail; /* 구매자Email */
	private String sellerEmail; /* 판매자Email */
	

} //class
