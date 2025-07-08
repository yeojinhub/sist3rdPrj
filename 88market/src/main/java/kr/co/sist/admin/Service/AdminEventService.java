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
import kr.co.sist.admin.util.Pagination;
import kr.co.sist.admin.util.SearchDTO;
import kr.co.sist.DTO.EventDTO;

@Service
public class AdminEventService {
    
    @Autowired
    private AdminEventDAO adminEventDAO;
    
    public List<EventDTO> getAllEvent(Pagination pagination, SearchDTO esDTO) {
    	int totalCount = adminEventDAO.selectTotalCount(esDTO);
    	pagination.setTotalCount(totalCount);
    	List<EventDTO> list = adminEventDAO.selectAllEvent(pagination, esDTO); 
    	
    	for (EventDTO eDTO : list) {
    		eDTO.setIDTO(adminEventDAO.selectAllImage(eDTO.getImgNum()));
    	}
    	
        return list;
    }

    public void deleteEvents(List<Integer> evtNums) {
        adminEventDAO.deleteEvents(evtNums);

        for (int evtNum : evtNums) {
            int imgNum = adminEventDAO.selectEventImageNum(evtNum);
            adminEventDAO.deleteEventImage(imgNum);
        }
    }

    public EventDTO getEventDetail(int evtNum) {
        EventDTO eventDTO = adminEventDAO.selectEventDetail(evtNum);
        eventDTO.setIDTO(adminEventDAO.selectAllImage(eventDTO.getImgNum()));
        return eventDTO;
    }

    public void updateEvent(EventDTO eventDTO, MultipartFile thumbnailImage, MultipartFile mainImage) {
        adminEventDAO.updateEvent(eventDTO);

        if (thumbnailImage.isEmpty() && mainImage.isEmpty()) {
            return;
        }

        String uploadDir = createUploadDir(eventDTO.getEvtNum());
        try {
            String thumbnailPath = uploadFile(thumbnailImage, uploadDir, "thumbnail");
            String mainImagePath = uploadFile(mainImage, uploadDir, "main");

            thumbnailPath = thumbnailPath.substring(thumbnailPath.indexOf("static") + 6);
            mainImagePath = mainImagePath.substring(mainImagePath.indexOf("static") + 6);

            Map<String, Object> params = new HashMap<>();
            params.put("imgNum", eventDTO.getImgNum());
            params.put("thumbnailPath", thumbnailPath);
            params.put("mainImagePath", mainImagePath);

            adminEventDAO.updateEventImage(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            String thumbnailPath = uploadFile(thumbnailImage, uploadDir, "thumbnail");
            String mainImagePath = uploadFile(mainImage, uploadDir, "main");
            
            thumbnailPath = thumbnailPath.substring(thumbnailPath.indexOf("static") + 6);
            mainImagePath = mainImagePath.substring(mainImagePath.indexOf("static") + 6);
            
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
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/events/upload/" + evtNum;
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        return uploadDir;
    }
    
    private String uploadFile(MultipartFile file, String uploadDir, String name) throws Exception {
    	String fileName = file.getOriginalFilename();
    	String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : "";
    	String newFileName = name + extension;
        String filePath = uploadDir + "/" + newFileName;
        
        File uploadFile = new File(filePath);
        file.transferTo(uploadFile);
        
        return filePath;
    }
}
