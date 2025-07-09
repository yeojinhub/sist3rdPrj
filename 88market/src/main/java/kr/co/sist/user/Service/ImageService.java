package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.DAO.ImageDAO;
import kr.co.sist.DTO.ImageDTO;

@Service
public class ImageService {

    @Autowired
    private ImageDAO imageDAO;

    public int getNextImageSeq() {
        return imageDAO.getNextImageSeq();
    }

    public void insertImage(ImageDTO imageDTO) {
        imageDAO.insert(imageDTO);
    }
}


