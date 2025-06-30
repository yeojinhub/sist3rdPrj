package kr.co.sist.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FaqDTO {
    private int faqNum;
    private int admNum;
    private String title;
    private String content;
    private String name;
    private String type;
    private Date inputDate;
}
