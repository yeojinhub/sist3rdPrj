package kr.co.sist.admin.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class PlaningPaginationDTO {
    private int pageNum = 1;     // 현재 페이지 (기본값 1)
    private int pageSize = 5;   // 페이지당 항목 수
    private int totalCount;      // 전체 데이터 개수
    private int totalPages;
    
    // Oracle 전용 계산 메서드
    public int getStartRow() {
        return (pageNum - 1) * pageSize;
    }
    public int getEndRow() {
        return pageNum * pageSize;
    }
    
    public PlaningPaginationDTO(int pageNum, int pageSize, int totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
    }
    

}
