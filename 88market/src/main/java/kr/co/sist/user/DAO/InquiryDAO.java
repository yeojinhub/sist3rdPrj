package kr.co.sist.user.DAO;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.InquiryDTO;

@Mapper
public interface InquiryDAO {
	public void insertInquiry(InquiryDTO iDTO);
	public void insertImage(Map<String, Object> map);
	public void updateInqNum(Map<String, Integer> map);
	public String selectUserInfo(int userNum);
}