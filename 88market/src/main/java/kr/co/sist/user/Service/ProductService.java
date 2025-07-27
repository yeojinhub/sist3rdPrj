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
    
    public ImageDTO selectImageByNum(Integer imgNum) {
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
    
    public List<ProductDTO> selectWishlistByUserNum(String userNum){
    	return favoriteDAO.selectWishlistByUserNum(userNum);
    }
    
    // 민경 - 마이페이지 내상품 (전체)
    public List<ProductDTO> getProductsByLoginId(String loginId) {
        return productDAO.selectByLoginId(loginId);
    }

    public UserDTO getUserByLoginId(String loginId) {
        return productDAO.getUserByLoginId(loginId);
    }

    public int getSafeByLoginId(String loginId) {
        return productDAO.getSafeByLoginId(loginId);
    }

    public int getReviewByLoginId(String loginId) {
        return productDAO.getReviewByLoginId(loginId);
    }
    
    // 민경 - wishlist 불러오기
    public List<ProductDTO> getWishlist(String userNum) {
        List<ProductDTO> wishlist = favoriteDAO.selectWishlistByUserNum(userNum);
    	 System.out.println("Wishlist from ProductService: " + wishlist);
        return favoriteDAO.selectWishlistByUserNum(userNum);
    }
    // 민경 - whishlist 삭제
    @Transactional
    public void removeFromWishlist(String userNum, String  productId) {
        System.out.println("Attempting to remove from wishlist: " + userNum + ", Product ID: " + productId);
        favoriteDAO.deleteFavorite(userNum,  productId);
    }
}


