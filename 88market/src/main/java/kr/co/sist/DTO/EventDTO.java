package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 이벤트 테이블의 정보를 저장하는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventDTO {
	
	private int evtNum; /* 이벤트번호 */
	private String title; /* 이벤트제목 */
	private String content; /* 이벤트내용 */
	private String name; /* 이벤트작성자 */
	private Date startDate; /* 이벤트시작일 */
	private Date endDate; /* 이벤트종료일 */
	private int evtType; /* 이벤트타입 */
	private int admNum; /* 관리자사번 */
	private String mainType; /* 메인화면 표시 여부 */
	private int imgNum; /* 이미지번호 */
	private ImageDTO iDTO;
	
} //class
