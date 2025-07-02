package kr.co.sist.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.FaqDTO;
import kr.co.sist.DTO.NoticeDTO;
import kr.co.sist.admin.util.FaqSearchDTO;
import kr.co.sist.admin.util.Pagination;

@Mapper
public interface AdminFaqDAO {
    public int selectTotalCount(@Param("fsDTO") FaqSearchDTO fsDTO);
    public List<FaqDTO> selectAllNotice(
        @Param("pagination") Pagination pagination, 
        @Param("fsDTO") FaqSearchDTO fsDTO
    );
    public void deleteFaqs(List<Integer> faqNums);
    public void insertFaq(FaqDTO faqDTO);
    public FaqDTO selectFaqDetail(@Param("faqNum") int faqNum);
    public void updateFaq(FaqDTO faqDTO);
}
