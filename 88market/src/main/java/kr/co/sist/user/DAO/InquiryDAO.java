package kr.co.sist.user.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.AnswerDTO;
import kr.co.sist.DTO.InquiryDTO;

@Mapper
public interface InquiryDAO {
	public void insertInquiry(InquiryDTO iDTO);
	public void insertImage(Map<String, Object> map);
	public void updateInqNum(Map<String, Integer> map);
	public String selectUserInfo(int userNum);
	public List<InquiryDTO> inquiryList(int userNum);
	public InquiryDTO inquiryDetail(int inqNum);
	public AnswerDTO inquiryAnswer(int inqNum);
}