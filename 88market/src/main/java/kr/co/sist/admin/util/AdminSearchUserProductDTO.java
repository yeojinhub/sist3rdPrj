package kr.co.sist.admin.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminSearchUserProductDTO {
	
	private String searchType; // 제목, 판매자
	private String keyword; // 키워드검색 
	private Integer catNum; // 카테고리 번호
	private String statusFilter; //상태 - 전체, 판매중, 예약중, 거래완료- 전체가 기본
	private String hideFilter=""; // 숨김 필터 - 전체가 기본

	// 페이징 파라미터
	private int pageNum  = 1;
	private int pageSize = 15;
	
}
