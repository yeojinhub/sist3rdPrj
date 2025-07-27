package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

	private String userNum; /* 사용자번호 */
	private String name; /* 사용자이름 */
	private String nickname; /* 사용자닉네임 */
	private String image; /* 사용자프로필사진 */
	private String email; /* 사용자이메일 */
	private String pass; /* 사용자닉네임 */
	private String tel; /* 사용자전화번호 */ 
	private String zipcode; /* 사용자우편번호 */
	private String address; /* 사용자주소 */
	
	private int manner; /* 사용자신뢰지수 */
	private int point; /* 사용자포인트 */
	
	private Date inputDate; /* 사용자가입일 */
	private Date banDate; /* 사용자정지일 */
	
	private String banType; /* 사용자정지여부(Y,N) */
    private Date drawDate; /* 사용자탈퇴일 */
    private String withdraw; /* 사용자탈퇴여부(Y,N) */
	
    private String authCode; /* 사용자인증번호 */
    
    private Integer totalReport; /* 사용자누적신고수 */
	
} //class
