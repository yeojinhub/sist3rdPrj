package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.DAO.ProductDAO;
import kr.co.sist.DTO.ProductDTO;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public int getNextProductSeq() {
        return productDAO.getNextProductSeq();
    }

    public void insertProduct(ProductDTO productDTO) {
        productDAO.insertProduct(productDTO);
    }
}

