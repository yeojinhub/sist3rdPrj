package kr.co.sist.admin.util;

import java.util.List;

import lombok.Data;

@Data
public class UserProductPagination<T> {

	    private int pageNum    = 1;     // 현재 페이지
	    private int pageSize   = 15;     // 페이지당 항목 수
	    private int totalCount;         // 전체 데이터 수
	    private List<T> items;          // 실제 조회된 데이터 리스트

	    public int getStartRow() {
	        return (pageNum - 1) * pageSize;
	    }
	    public int getEndRow() {
	        return pageNum * pageSize;
	    }
	    public int getTotalPages() {
	        if (pageSize == 0) return 0;
	        return (int) Math.ceil((double) totalCount / pageSize);
	    }

}
