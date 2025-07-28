package kr.co.sist.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.ReportDTO;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Mapper
public interface AdminReportDAO {
	public int selectTotalCount(@Param("nsDTO") SearchDTO nsDTO);
	
	public List<ReportDTO> selectAllReport(
	        @Param("pagination") Pagination pagination, 
	        @Param("nsDTO") SearchDTO nsDTO
	);
	
	public ReportDTO selectReportByNum(@Param("repNum") int repNum); 
	public void deleteReportByNum(@Param("repNum") int repNum);
}
