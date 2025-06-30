package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 상품 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {
	
	private String prdNum; /* 상품번호 */
	private String title; /* 상품제목 */
	private String content; /* 상품내용 */
	private int price; /* 상품가격 */
	private String location1; /* 상품등록지역 */
	private Date inputDate; /* 상품작성일 */
	private String appointType; /* 상품예약여부(Y,N) */
	private String hiddenType; /* 상품숨김여부(Y,N) */
	private String sellType; /* 상품판매여부(Y,N) */
	private int prdCnt; /* 상품재고 */
	private int clickNum; /* 상품조회수 */
	
	private String userNum; /* 사용자번호 */
	private int catNum; /* 카테고리번호 */
	private String comNum; /* 사업자등록번호 */
	private int imgNum; /* 이미지번호 */

} //class
