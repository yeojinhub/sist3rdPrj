package kr.co.sist.admin.util;

import lombok.Data;

@Data
public class Pagination {
    private int pageNum = 1;     // 현재 페이지 (기본값 1)
    private int pageSize = 5;   // 페이지당 항목 수
    private int totalCount;      // 전체 데이터 개수
    
    // Oracle 전용 계산 메서드
    public int getStartRow() {
        return (pageNum - 1) * pageSize;
    }
    public int getEndRow() {
        return pageNum * pageSize;
    }

    // 전체 페이지 수 반환 (타임리프에서 사용)
    public int getTotalPages() {
        if (pageSize == 0) return 0;
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
