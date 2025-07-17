package kr.co.sist.user.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.user.DAO.FavoriteDAO;
import kr.co.sist.user.DAO.ImageDAO;
import kr.co.sist.user.DAO.ProductDAO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.UserDTO;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private ImageDAO imageDAO;
    
    @Autowired
    private FavoriteDAO favoriteDAO;
    
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
    
    public ProductDTO selectProductByNum(String prdNum) {
        return productDAO.selectProductByNum(prdNum);
    }
    
    public ImageDTO selectImageByNum(int imgNum) {
    	return imageDAO.selectImageByNum(imgNum);
    }
    
    public int countChatroomsByPrdNum(String prdNum) {
    	return productDAO.countChatroomsByPrdNum(prdNum);
    }
    
    public void increaseClickNum(String prdNum) {
        productDAO.increaseClickNum(prdNum);
    }
    
    public UserDTO getUser(String userNum) {
    	return productDAO.getUser(userNum);
    }
    
    public int getSafeByUserNum(@Param("userNum")String userNum) {
    	return productDAO.getSafeByUserNum(userNum);
    }
    
    public int getReviewByUserNum(@Param("userNum")String userNum) {
    	return productDAO.getReviewByUserNum(userNum);
    }
    
    public ProductDTO selectRandomProduct(String userNum, String excludePrdNum) {
    	return productDAO.selectRandomProduct(userNum, excludePrdNum);
    }
    
    public List<ProductDTO> getRelatedProducts(int catNum, String prdNum) {
        return productDAO.selectRelatedProducts(catNum, prdNum);
    }
    
    public boolean checkFavorite(String userNum, String prdNum) {
    	return favoriteDAO.checkFavorite(userNum, prdNum);
    	
    }
    
    public void likeProduct(String userNum, String prdNum) {
    	favoriteDAO.insertFavorite(userNum, prdNum);
    	productDAO.increaseLikeNum(prdNum);
    	
    }
    
    @Transactional
    public void unlikeProduct(String userNum, String prdNum) {
        favoriteDAO.deleteFavorite(userNum, prdNum);
        productDAO.decreaseLikeNum(prdNum);
    }
}


