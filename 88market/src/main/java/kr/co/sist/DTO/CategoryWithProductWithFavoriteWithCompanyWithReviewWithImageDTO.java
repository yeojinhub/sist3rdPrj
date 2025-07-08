package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 카테고리 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryWithProductWithFavoriteWithCompanyWithReviewWithImageDTO {
	
	private String coName; /* 카테고리이름 */
	
	private String prdNum; /* 상품번호 */
	private String title; /* 상품제목 */
	private String content; /* 상품내용 */
	private int price; /* 상품가격 */
	private String location1; /* 상품등록지역 */
	private Date prdInputDate; /* 상품작성일 */
	private String appointType; /* 상품예약여부(Y,N) */
	private String hiddenType; /* 상품숨김여부(Y,N) */
	private String sellType; /* 상품판매여부(Y,N) */
	private String deliveryType; /* 상품거래여부(Y,N) */
	private int prdCnt; /* 상품재고 */
	private int clickNum; /* 상품조회수 */
	
	private String userNum; /* 사용자번호 */
	private int catNum; /* 카테고리번호 */
	private String comNum; /* 사업자등록번호 */
	private int imgNum; /* 이미지번호 */
	
	private int favNum; /* 찜번호 */
	private Date input_date; /* 찜저장일 */
	
	private String comName; /* 기업이름 */
	private String ceoName; /* 기업대표자명 */
	private String name; /* 기업담당자명 */
	private String image; /* 기업프로필사진 */
	private String id; /* 기업ID */
	private String pass; /* 기업비밀번호 */
	private String tel; /* 기업전화번호 */
	private String zipcode; /* 기업우편번호 */
	private String address; /* 기업주소 */
	
	private Date inputDate; /* 기업가입일 */
	private Date drawDate; /* 기업탈퇴일 */
	
	private String withdraw; /* 기업탈퇴여부(Y,N) */
	
	private int revNum; /* 후기번호 */
	private String reContent; /* 후기내용 */
	private String reName; /* 후기작성자 */
	private Date reInputDate; /* 후기작성일 */
	private int reportCnt; /* 누적신고수 */
	private String reHiddenType; /* 후기숨김여부(Y,N) */
	private String keyword; /* 후기선택키워드 */
	private int reviewCount; /* 리뷰수 */
	
	private String mainImage; /* 메인이미지 */
	private String subImage1; /* 추가이미지1 */
	private String subImage2; /* 추가이미지2 */
	private String subImage3; /* 추가이미지3 */
	private String subImage4; /* 추가이미지4 */
	private String imageType; /* 이미지게시물타입(1,2,3)*/


} //class
