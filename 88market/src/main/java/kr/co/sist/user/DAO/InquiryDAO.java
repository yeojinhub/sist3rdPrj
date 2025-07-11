package kr.co.sist.user.DAO;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.InquiryDTO;

@Mapper
public interface InquiryDAO {
	public void insertInquiry(InquiryDTO iDTO);
	public void insertImage(ImageDTO imgDTO);
	public void updateInqNum(Map<String, Integer> map);
}