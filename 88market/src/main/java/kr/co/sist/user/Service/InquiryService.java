package kr.co.sist.user.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	public boolean addInquiry(InquiryDTO iDTO, MultipartFile[] files) {
		try {
			// 0. 일단 유저 정보 가데이터 넣어두자.
			iDTO.setUserNum(1);
			iDTO.setName("홍길동");
			
			// 1. 텍스트데이터 부터 넣어보자.
			iDAO.insertInquiry(iDTO);
			
			// 2. 파일 업로드
			// 2-1. 업로드 디렉토리 생성 및 변수 저장
			String uploadDir = createUploadDir(iDTO.getInqNum());

			// 2-2. 이미지DTO 생성 및 기본 값 설정
			ImageDTO imgDTO = new ImageDTO();
			imgDTO.setImageType("INQUIRY");
			for (int i = 0; i < files.length; i++) {
				String filePath = uploadFile(files[i], uploadDir, String.valueOf(i));
				switch (i) {
					case 0: 
						imgDTO.setMainImage(filePath);
						break;
					case 1:
						imgDTO.setSubImage1(filePath);
						break;
					case 2:
						imgDTO.setSubImage2(filePath);
						break;
				}// end switch-case
			}// end for

			// 3. 이미지DTO 데이터 넣어보자.
			iDAO.insertImage(imgDTO);

			// 4. 문의에 IMG_NUM을 업데이트 해주자.
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("imgNum", imgDTO.getImgNum());
			map.put("inqNum", iDTO.getInqNum());
			iDAO.updateInqNum(map);
			
			// 5. 완료 true 반환!
			return true;
		} catch (Exception e) {
			return false;
		}
	}// addInquiry
	
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
	
	private String uploadFile(MultipartFile file, String uploadDir, String name) throws Exception {
		String fileName = file.getOriginalFilename();
		String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : "";
		String newFileName = name + extension;
		String filePath = uploadDir + "/" + newFileName;

		File uploadFile = new File(filePath);
		file.transferTo(uploadFile);

		return filePath;
	}// uploadFile
	
}// class
