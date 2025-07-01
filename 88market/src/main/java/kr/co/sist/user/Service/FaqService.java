package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.*;
import kr.co.sist.user.DAO.FaqDAO;

@Service
public class FaqService {
	@Autowired
	private FaqDAO faqDAO;
	
	public List<FaqDTO> selectFaqList() {
		return faqDAO.selectFaqList();
	}
	
	public List<FaqDTO> selectFaqListByType(String type) {
		return faqDAO.selectFaqListByType(type);
	}
	
	public List<FaqDTO> selectFaqListByKeyword(String keyword) {
		return faqDAO.selectFaqListByKeyword(keyword);
	}
}
