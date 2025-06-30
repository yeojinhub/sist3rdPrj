package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FaqDTO {
    private int faqNum;
    private int admNum;
    private String title;
    private String content;
    private String name;
    private String type;
    private Date inputDate;
}
