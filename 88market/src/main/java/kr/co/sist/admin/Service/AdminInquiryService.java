package kr.co.sist.admin.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.DTO.AnswerDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.InquiryDTO;
import kr.co.sist.admin.DAO.AdminInquiryDAO;
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;

@Service
public class AdminInquiryService {

	@Autowired
	private AdminInquiryDAO adminInquiryDAO;

	public List<InquiryDTO> getAllInquiry(Pagination pagination, SearchDTO sDTO) {
		int totalCount = adminInquiryDAO.selectTotalCount(sDTO);
		pagination.setTotalCount(totalCount);
		List<InquiryDTO> list = adminInquiryDAO.selectAllInquiry(pagination, sDTO);
		return list;
	}// getAllInquiry

	public InquiryDTO getInquiryByInqNum(int inqNum) {
		InquiryDTO inquiry = adminInquiryDAO.selectInquiryByInqNum(inqNum);

		if (inquiry.getStatusType().equals("답변완료")) {
			inquiry.setAnswerDTO(adminInquiryDAO.selectAnswerByInqNum(inqNum));
		} // end if

		if (inquiry.getImgNum() != null) {
			inquiry.setImageDTO(adminInquiryDAO.selectImageByImgNum(inquiry.getImgNum()));
		} // end if

		return inquiry;
	}// getInquiryByInqNum

	@Transactional
	public void addAnswer(AnswerDTO answerDTO) {

		// 1. 답변 등록
		adminInquiryDAO.insertAnswer(answerDTO);

		// 2. 문의 상태 변경
		Map<String, Object> map = new HashMap<>();
		map.put("inqNum", answerDTO.getInqNum());
		map.put("statusType", "답변완료");
		map.put("admNum", answerDTO.getAdmNum());
		adminInquiryDAO.updateInquiryStatus(map);

	}// addAnswer

	@Transactional
	public void modifyAnswer(AnswerDTO answerDTO) {
		
		// 1. 답변 수정
		adminInquiryDAO.updateAnswer(answerDTO);

		// 2. 문의 상태 변경
		adminInquiryDAO.updateInquiryStatusModify(answerDTO.getAdmNum(), answerDTO.getInqNum());

	}// modifyAnswer

	@Transactional
	public void deleteInquiry(int inqNum) {
		// 1. 업로드 이미지 삭제
		ImageDTO imageDTO = adminInquiryDAO.selectImageByImgNum(adminInquiryDAO.selectInquiryByInqNum(inqNum).getImgNum());

		// 1-1. 이미지 테이블에서 삭제
		if (imageDTO != null) {
			adminInquiryDAO.deleteImage(imageDTO.getImgNum());
		}// end if

		// 1-2. 업로드 이미지 디렉터리 삭제
		String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/inquiry/upload/"
				+ inqNum;
		deleteDirectoryRecursively(new File(uploadDir));

		// 2. 답변 테이블에서 삭제
		adminInquiryDAO.deleteAnswer(inqNum);

		// 3. 문의 테이블에서 삭제
		adminInquiryDAO.deleteInquiry(inqNum);
	}// deleteAnswer

	// 디렉토리 내부 이미지파일 삭제 메소드
	private void deleteDirectoryRecursively(File dir) {
		try {
			if (dir.exists()) {
				File[] files = dir.listFiles();
				if (files != null) {
					for (File file : files) {
						if (file.isDirectory()) {
							deleteDirectoryRecursively(file);
						} else {
							if (!file.delete()) {
								System.err.println("파일 삭제 실패: " + file.getAbsolutePath());
							}
						}
					}
				}
				if (!dir.delete()) {
					System.err.println("디렉터리 삭제 실패: " + dir.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			System.err.println("디렉터리 삭제 중 오류: " + e.getMessage());
		}
	}

}// class
