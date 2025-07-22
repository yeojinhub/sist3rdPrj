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
    private String statusFilter; // all-전체, active-판매중, inactive-거래진행중, sold-결제완료
    private int pageNum = 1;
    private int pageSize = 10;
}
