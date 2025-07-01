package kr.co.sist.admin.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeSearchDTO {
    private String searchType;
    private String keyword;
}
