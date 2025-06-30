package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 찜 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteDTO {

	private int fav_num; /* 찜번호 */
	private Date input_date; /* 찜저장일 */
	
	private int prdNum; /* 상품번호 */ 
	private int userNum; /* 사용자번호 */

} //class
