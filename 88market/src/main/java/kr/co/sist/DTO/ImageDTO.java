package kr.co.sist.DTO;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 이미지 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageDTO {
	
	private int imgNum; /* 이미지번호 */
	private String mainImage; /* 메인이미지 */
	private String subImage1; /* 추가이미지1 */
	private String subImage2; /* 추가이미지2 */
	private String subImage3; /* 추가이미지3 */
	private String subImage4; /* 추가이미지4 */
	private String imageType; /* 이미지게시물타입(1,2,3)*/

} //class
