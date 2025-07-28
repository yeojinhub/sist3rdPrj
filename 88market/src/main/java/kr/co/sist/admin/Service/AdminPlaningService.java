package kr.co.sist.admin.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.OrderManageDTO;
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

    	 // 재고 0이면 자동으로 판매상태를 '매진'으로 전환
        if (productDTO.getPrdCnt() == 0) {
            productDTO.setSellType("N");
        }
    	
    	// 이미지 번호 시퀀스 받기
        int imgSeq = getNextImageSeq();
        imageDTO.setImgNum(imgSeq);

        // 이미지 먼저 삽입
        adminPlaningDAO.insertImage(imageDTO);

        // 상품에 이미지 번호 세팅 후 삽입
        productDTO.setImgNum(imgSeq);
        adminPlaningDAO.insertProduct(productDTO);
    }
    
    public ProductDTO getProductByPrdNum(String prdNum) {
        return adminPlaningDAO.getProductByPrdNum(prdNum);
    }
    //----------
    /** ❶ 상품 + 이미지 수정 */
    public void updateProductWithImages(ProductDTO dto,
                                        List<MultipartFile> newImages) throws IOException {
    	
    	// 재고 0이면 자동으로 판매상태를 '매진'으로 전환
    	if (dto.getPrdCnt() == 0) {
    	    dto.setSellType("N");
    	}
    	
    	/* 1) 상품 기본 정보만 먼저 업데이트  ─ imgNum은 건드리지 않음 */
    	adminPlaningDAO.updateProduct(dto);

    	/* 2) 새 이미지가 넘어온 경우에만 처리 */
    	if (newImages != null && !newImages.isEmpty()) {

    	    /* ➡ 새 이미지 번호 무조건 발급! */
    	    int newImgNum = getNextImageSeq();
    	    dto.setImgNum(newImgNum);          // 상품 DTO에 새 번호 세팅

    	    /* 2‑1) 실제 파일 저장 + ImageDTO 만들기 */
    	    ImageDTO imgDto = buildImageDTO(newImgNum, newImages, dto.getPrdNum());

    	    /* 2‑2) IMAGE 테이블 INSERT (새 번호니까 insert면 충분) */
    	    adminPlaningDAO.insertImage(imgDto);

    	    /* 2‑3) PRODUCT 테이블에 img_num 갱신 */
    	    adminPlaningDAO.updateProductImgNum(dto.getPrdNum(), newImgNum);
    	}
        
        
    }

    /* ❷ 파일 저장 + ImageDTO 조립 (등록 때 쓰던 것 재활용) */
    private ImageDTO buildImageDTO(int imgNum, List<MultipartFile> files,
                                   String prdNum) throws IOException {

        if (files == null || files.isEmpty()) return null;

        ImageDTO img = new ImageDTO();
        img.setImgNum(imgNum);
        img.setImageType("1");

        Path dir = Paths.get("src/main/resources/static/images/product", prdNum);
        Files.createDirectories(dir);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile f = files.get(i);
            String ext = Objects.requireNonNull(f.getOriginalFilename())
                                .substring(f.getOriginalFilename().lastIndexOf('.'));
            String save = prdNum + "_" + (i + 1) + ext;
            Path   tgt  = dir.resolve(save);
            f.transferTo(tgt);

            String web = "/images/product/" + prdNum + "/" + save;
            switch (i) {
                case 0 -> img.setMainImage(web);
                case 1 -> img.setSubImage1(web);
                case 2 -> img.setSubImage2(web);
                case 3 -> img.setSubImage3(web);
                case 4 -> img.setSubImage4(web);
            }
        }
        return img;
    }
    
    public ImageDTO getImageByImgNum(int imgNum) {
        return adminPlaningDAO.selectImageByImgNum(imgNum);
    }
    
    @Transactional
    public void deleteProductByPrdNum(String prdNum) {
        // 먼저 이미지 삭제 → 상품 삭제
        adminPlaningDAO.deleteImageByPrdNum(prdNum);
        adminPlaningDAO.deleteProductByPrdNum(prdNum);
    }
    
    // 주문 리스트 조회
//    public List<OrderManageDTO> getOrderList(String comNum, String keyword, String tradeStatus, int startRow, int endRow) {
//        
//    	
//    	return adminPlaningDAO.selectOrdersByCompany(comNum, keyword, tradeStatus, startRow, endRow);
//    }
    public List<OrderManageDTO> getOrderList(String accountType, String comNum,String keyword, String tradeStatus,int startRow, int endRow) {
		if ("ADMIN".equalsIgnoreCase(accountType)) {
		return adminPlaningDAO.selectOrdersForAdmin(keyword, tradeStatus, startRow, endRow);
		} else {
		return adminPlaningDAO.selectOrdersByCompany(comNum, keyword, tradeStatus, startRow, endRow);
		}
	}

    // 주문 개수 조회
//    public int getOrderCount(String comNum, String keyword, String tradeStatus) {
//        return adminPlaningDAO.countOrdersByCompany(comNum, keyword, tradeStatus);
//    }
    public int getOrderCount(String accountType, String comNum, String keyword, String tradeStatus) {
		if ("ADMIN".equalsIgnoreCase(accountType)) {
		return adminPlaningDAO.countOrdersForAdmin(keyword, tradeStatus);
		} else {
		return adminPlaningDAO.countOrdersByCompany(comNum, keyword, tradeStatus);
		}
	}

    // 거래 상세 조회
    public OrderManageDTO getOrderDetail(String tradeId) {
        return adminPlaningDAO.selectOrderDetailByTradeId(tradeId);
    }

    // 거래 상태 변경
    public void updateTradeStatus(String tradeId, String tradeStatus) {
        adminPlaningDAO.updateTradeStatus(tradeId, tradeStatus);
    }
    
    // 관리자 전체 상품 검색
    public List<ProductDTO> searchAllProducts(String keyword, String hidden, int startRow, int endRow) {
        return adminPlaningDAO.searchAllProducts(keyword, hidden, startRow, endRow);
    }

    // 관리자 전체 상품 수 조회
    public int getAllProductCount(String keyword, String hidden) {
        return adminPlaningDAO.getAllProductCount(keyword, hidden);
    }
    
    public void updateTradeStatus(OrderManageDTO dto) {
        adminPlaningDAO.updateTradeStatus(dto);
    }
    
}
