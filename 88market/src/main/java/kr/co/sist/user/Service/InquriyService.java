package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.DAO.InquiryDAO;

@Service
public class InquriyService {

	@Autowired
	private InquiryDAO iDAO;
	
}
