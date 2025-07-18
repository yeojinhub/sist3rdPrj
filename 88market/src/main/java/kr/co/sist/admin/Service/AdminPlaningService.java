package kr.co.sist.admin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.admin.DAO.AdminPlaningDAO;


@Service
public class AdminPlaningService {

	@Autowired
	private AdminPlaningDAO adminPlaningDAO;
	
	public String getComNumById(String id) {
        return adminPlaningDAO.selectComNumById(id);
    }
	
	public List<ProductDTO> searchProductsByCondition(String comNum, String keyword, String hidden, int startRow, int endRow) {
        return adminPlaningDAO.searchProductsByCondition(comNum, keyword, hidden, startRow, endRow);
    }

    public int getTotalCountByCondition(String comNum, String keyword, String hidden) {
        return adminPlaningDAO.getTotalCountByCondition(comNum, keyword, hidden);
    }
	
    //카테고리
    public List<CategoryDTO> getAllCategories() {
        return adminPlaningDAO.selectAllCategories();
    }
    //상품등록
    public int insertProduct(ProductDTO product) {
        return adminPlaningDAO.insertProduct(product);
    }
    //이미지등록
    public int insertImage(ImageDTO image) {
        return adminPlaningDAO.insertImage(image);
    }
    
    public int getNextProductSeq() {
        return adminPlaningDAO.getNextProductSeq();
    }
    
    public int getNextImageSeq() {
        return adminPlaningDAO.getNextImageSeq();
    }
    
    @Transactional
    public void insertProductWithImages(ProductDTO productDTO, ImageDTO imageDTO) {

    	// 이미지 번호 시퀀스 받기
        int imgSeq = getNextImageSeq();
        imageDTO.setImgNum(imgSeq);

        // 이미지 먼저 삽입
        adminPlaningDAO.insertImage(imageDTO);

        // 상품에 이미지 번호 세팅 후 삽입
        productDTO.setImgNum(imgSeq);
        adminPlaningDAO.insertProduct(productDTO);
 //    	adminPlaningDAO.insertImage(imageDTO);
//    	adminPlaningDAO.insertProduct(productDTO);
    }
    
}
