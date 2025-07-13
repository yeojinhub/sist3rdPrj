package kr.co.sist.admin.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.AnswerDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.InquiryDTO;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Mapper
public interface AdminInquiryDAO {
    public int selectTotalCount(@Param("sDTO") SearchDTO sDTO);
    public List<InquiryDTO> selectAllInquiry(
        @Param("pagination") Pagination pagination, 
        @Param("sDTO") SearchDTO sDTO
        );
    public InquiryDTO selectInquiryByInqNum(@Param("inqNum") int inqNum);
    public AnswerDTO selectAnswerByInqNum(@Param("inqNum") int inqNum);
    public ImageDTO selectImageByImgNum(@Param("imgNum") int imgNum);
    public String selectAdmNameByAdmNum(@Param("admNum") int admNum);
    public void insertAnswer(AnswerDTO answerDTO);
    public void updateInquiryStatus(@Param("map") Map<String, Object> map);
    public void updateAnswer(AnswerDTO answerDTO);
    public void updateInquiryStatusModify(@Param("admNum") int admNum, @Param("inqNum") int inqNum);
    public void deleteAnswer(@Param("inqNum") int inqNum);
    public void deleteInquiry(@Param("inqNum") int inqNum);
    public void deleteImage(@Param("imgNum") int imgNum);
}
