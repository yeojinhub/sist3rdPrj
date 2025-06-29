package kr.co.sist.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeDTO {
    private int notNum;
    private int admNum;
    private String title;
    private String content;
    private String name;
    private Date inputDate;
    private String statusType;

}
