package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.user.DAO.ImageDAO;
import kr.co.sist.user.DAO.ProductDAO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private ImageDAO imageDAO;
    
    public int getNextProductSeq() {
        return productDAO.getNextProductSeq();
    }
    
    public int getNextImageSeq() {
        return imageDAO.getNextImageSeq();
    }
    
    @Transactional
    public void insertProductWithImages(ProductDTO productDTO,ImageDTO imageDTO) {
    	imageDAO.insert(imageDTO);
        productDAO.insert(productDTO);
    }
}


