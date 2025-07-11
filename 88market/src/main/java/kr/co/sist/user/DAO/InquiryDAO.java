package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.InquiryDTO;

@Mapper
public interface InquiryDAO {
	public int insertInquiry(InquiryDTO iDTO);
}
