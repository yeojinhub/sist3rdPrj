package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.DAO.BuyDAO;

@Service
public class BuyService {
    
    @Autowired
    private BuyDAO buyDAO;

    public ProductDTO searchProduct(String prdNum) {
        return buyDAO.selectProduct(prdNum);
    }// searchProduct

    public ImageDTO searchImage(int imgNum) {
        return buyDAO.selectImage(imgNum);
    }// searchImage
}// class
