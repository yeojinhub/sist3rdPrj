package kr.co.sist.admin.Service;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.admin.DAO.AdminEventDAO;
import kr.co.sist.DTO.EventDTO;
import kr.co.sist.DTO.ImageDTO;

@Service
public class AdminEventService {
    
    @Autowired
    private AdminEventDAO adminEventDAO;
    
    public List<EventDTO> getAllEvent() {
        return adminEventDAO.selectAllEvent();
    }
    
    public List<ImageDTO> getAllImage() {
        return adminEventDAO.selectAllImage();
    }
    
    @Transactional
    public boolean addEvent(EventDTO eventDTO, MultipartFile thumbnailImage, MultipartFile mainImage) {
        try {
            // 1. 이벤트 등록 (insert 리턴값 말고 DTO에서 가져오기)
            // 1-1. 임시로 name과 admNum 가데이터.
            eventDTO.setName("강태일");
            eventDTO.setAdmNum(1);
            adminEventDAO.insertEvent(eventDTO);
            
            // 2. 디렉터리 생성 및 파일 업로드
            String uploadDir = createUploadDir(eventDTO.getEvtNum());
            String thumbnailPath = uploadFile(thumbnailImage, uploadDir);
            String mainImagePath = uploadFile(mainImage, uploadDir);
            
            // 3. 이미지 정보 등록
            Map<String, Object> imageParams = new HashMap<>();
            imageParams.put("thumbnailPath", thumbnailPath);
            imageParams.put("mainImagePath", mainImagePath);
            imageParams.put("imageType", "EVENT");
            
            adminEventDAO.insertImage(imageParams);
            int imgNum = (Integer) imageParams.get("imgNum"); // selectKey로 설정된 값
            
            // 4. 이벤트 테이블 imgNum 업데이트
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("evtNum", eventDTO.getEvtNum());
            updateParams.put("imgNum", imgNum);
            
            adminEventDAO.updateEventImgNum(updateParams);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private String createUploadDir(int evtNum) {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String uploadDir = "images/event/upload/" + evtNum + "_" + dateStr;
        
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        return uploadDir;
    }
    
    private String uploadFile(MultipartFile file, String uploadDir) throws Exception {
        String fileName = file.getOriginalFilename();
        String filePath = uploadDir + "/" + fileName;
        
        File uploadFile = new File(filePath);
        file.transferTo(uploadFile);
        
        return filePath;
    }
}
