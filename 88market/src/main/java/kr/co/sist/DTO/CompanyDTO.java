package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 기업 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyDTO {

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
	
	private Date inputDate; /* 기업가입일 */
	private Date drawDate; /* 기업탈퇴일 */
	
	private String withdraw; /* 기업탈퇴여부(Y,N) */

} //class
