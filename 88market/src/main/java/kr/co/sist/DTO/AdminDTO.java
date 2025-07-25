package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 관리자 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminDTO {
	
	private String admNum; /* 관리자사번 */
	private String name; /* 관리자이름 */
	private String id; /* 관리자ID */
	private String pass; /* 관리자비밀번호 */
	private String tel; /* 관리자전화번호 */
	
	private Date inputDate; /* 관리자가입일 */
	private Date banDate; /* 관리자정지일 */
	
	private String banType; /* 관리자정지여부(Y,N) */
	private int rollType; /* 관리자계정타입(1,2) */

} //class
