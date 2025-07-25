package kr.co.sist.user.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.InquiryDTO;
import kr.co.sist.user.DAO.InquiryDAO;

@Service
public class InquiryService {

	@Autowired
	private InquiryDAO iDAO;
	
	/**
	 * 문의사항을 등록해보자.
	 * 트랜젝션 처리로 문의테이블과 이미지테이블 두개 등록이 완성되어야 처리된다.
	 * @param iDTO
	 * @param files
	 */
	@Transactional
	public boolean addInquiry(InquiryDTO iDTO, MultipartFile[] files, UserDetails user) {
		try {
			// 1. 유저 정보 설정
			iDTO.setUserNum(Integer.parseInt(user.getUsername()));
			iDTO.setName(iDAO.selectUserInfo(Integer.parseInt(user.getUsername())));
			
			// 1. 문의 등록
			iDAO.insertInquiry(iDTO);


			// 1-1. 첨부이미지 없이 문의 등록 시 업로드 처리 안함
			if (files == null || files.length == 0) {
				return true;
			}// end if
	
			// 2. 파일 업로드 및 경로 처리
			String uploadDir = createUploadDir(iDTO.getInqNum());
			
			// 2-1. 경로들 미리 준비
			String mainImagePath = null;
			String subImage1Path = null;
			String subImage2Path = null;
			
			for (int i = 0; i < files.length; i++) {
				String filePath = uploadFile(files[i], uploadDir, i);
				filePath = filePath.substring(filePath.indexOf("static") + 6); // 상대경로 변환
				
				switch (i) {
					case 0: 
						mainImagePath = filePath;
						break;
					case 1:
						subImage1Path = filePath;
						break;
					case 2:
						subImage2Path = filePath;
						break;
				}
			}
	
			// 3. 이미지 정보 등록 (addEvent 스타일)
			Map<String, Object> imageParams = new HashMap<>();
			imageParams.put("imageType", "INQUIRY");
			imageParams.put("mainImage", mainImagePath != null ? mainImagePath : "");
			imageParams.put("subImage1", subImage1Path != null ? subImage1Path : "");  // null이어도 됨
			imageParams.put("subImage2", subImage2Path != null ? subImage2Path : "");  // null이어도 됨
			
			iDAO.insertImage(imageParams);
			int imgNum = (Integer) imageParams.get("imgNum"); // selectKey 값
	
			// 4. 문의 테이블 imgNum 업데이트
			Map<String, Integer> updateParams = new HashMap<>();
			updateParams.put("imgNum", imgNum);
			updateParams.put("inqNum", iDTO.getInqNum());
			iDAO.updateInqNum(updateParams);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace(); // 전체 스택트레이스 출력
			return false;
		}
	}// addInquiry

	/**
	 * 문의 리스트 조회
	 * @param user
	 * @return
	 */
	public List<InquiryDTO> inquiryList(UserDetails user) {

		// UserDetails에서 유저넘버 추출
		int userNum = Integer.parseInt(user.getUsername());

		return iDAO.inquiryList(userNum);
	}// inquiryList

	/**
	 * 문의 상세 조회
	 * @param inqNum
	 * @return
	 */
	public InquiryDTO inquiryDetail(int inqNum) {
		InquiryDTO iDTO = iDAO.inquiryDetail(inqNum);

		if (!iDTO.getStatusType().equals("대기")) {
			iDTO.setAnswerDTO(iDAO.inquiryAnswer(inqNum));
		}

		return iDTO;
	}// inquiryDetail
	
	/**
	 * 업로드 디렉터리 생성
	 * @param evtNum 디렉터리명에 inqNum을 넣어 만듭니다.
	 * @return 업로드 디렉터리 경로를 반환합니다.
	 */
	private String createUploadDir(int inqNum) {
		String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/inquiry/upload/" + inqNum;
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return uploadDir;
	}// createUploadDir
	
	/**
	 * 파일 업로드
	 * @param file 업로드할 파일
	 * @param uploadDir 업로드 디렉터리
	 * @param name 파일 이름
	 * @return 파일 업로드 경로를 반환합니다.
	 */
	private String uploadFile(MultipartFile file, String uploadDir, int name) throws Exception {
		String fileName = file.getOriginalFilename();
		String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : "";
		String newFileName = name + extension;
		String filePath = uploadDir + "/" + newFileName;

		File uploadFile = new File(filePath);
		file.transferTo(uploadFile);

		return filePath;
	}// uploadFile
	
}// class
