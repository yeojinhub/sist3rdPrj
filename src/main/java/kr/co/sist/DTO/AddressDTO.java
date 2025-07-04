package kr.co.sist.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 배송지 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressDTO {

	private int addrNum; /* 배송지번호 */
	private String name; /* 주문자 */
	private String recipientName; /* 받는사람 */
	private String tel; /* 전화번호 */
	private String zipcode; /* 우편번호 */
	private String address; /* 주소 */
	private String addrType; /* 대표배송지여부(Y,N) */
	
	private String userNum; /* 사용자번호 */
	
} //class
