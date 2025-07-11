package kr.co.sist.DTO;

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
public class CategoryDTO {
	
	private int catNum; /* 카테고리번호 */
	private String name; /* 카테고리이름 */

} //class
