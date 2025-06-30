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
    private String title;
    private String content;
    private String name;
    private String faqType;
    private Date inputDate;
    private int admNum;
}
