package kr.co.sist.admin.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminSearchUserTradeDTO {
    private String searchType;   // buyer, seller 등
    private String keyword;
    private int pageNum = 1;
    private int pageSize = 10;
}
