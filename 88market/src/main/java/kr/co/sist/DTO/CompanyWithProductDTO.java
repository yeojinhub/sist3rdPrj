package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyWithProductDTO {

	private String comNum; /* 사업자등록번호 */
	private String comName; /* 기업이름 */
	private String ceoName; /* 기업대표자명 */
	private String name; /* 기업담당자명 */
	private String image; /* 기업프로필사진 */
	private String id; /* 기업ID */
	private String pass; /* 기업비밀번호 */
	private String tel; /* 기업전화번호 */
	private String zipcode; /* 기업우편번호 */
	private String address; /* 기업주소 */
	
	private Date drawDate; /* 기업탈퇴일 */
	
	private String withdraw; /* 기업탈퇴여부(Y,N) */
	
	private String prdNum; /* 상품번호 */
	private String title; /* 상품제목 */
	private String content; /* 상품내용 */
	private Integer  price; /* 상품가격 */
	private String location1; /* 상품등록지역 */
	private Date inputDate; /* 상품작성일 */
	private String appointType; /* 상품예약여부(Y,N) */
	private String hiddenType; /* 상품숨김여부(Y,N) */
	private String sellType; /* 상품판매여부(Y,N) */
	private Integer  prdCnt; /* 상품재고 */
	private Integer  clickNum; /* 상품조회수 */
	
	private String userNum; /* 사용자번호 */
	private Integer  catNum; /* 카테고리번호 */
	private Integer  imgNum; /* 이미지번호 */
	
}
