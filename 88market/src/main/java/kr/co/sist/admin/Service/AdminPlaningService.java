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
    }
    
    public ProductDTO getProductByPrdNum(String prdNum) {
        return adminPlaningDAO.getProductByPrdNum(prdNum);
    }
    //----------
    /** ❶ 상품 + 이미지 수정 */
    public void updateProductWithImages(ProductDTO dto,
                                        List<MultipartFile> newImages) throws IOException {

        /* 1)  상품 기본 정보 UPDATE */
        adminPlaningDAO.updateProduct(dto);   // 제목·가격·노출여부 등

        /* 2)  이미지가 넘어온 경우만 처리 */
        if (newImages != null && !newImages.isEmpty()) {

            int imgNum = dto.getImgNum() == 0
                       ? getNextImageSeq() : dto.getImgNum();
            dto.setImgNum(imgNum);

            // ⇒ 실제 파일 저장 + ImageDTO 생성
            ImageDTO imgDto = buildImageDTO(imgNum, newImages, dto.getPrdNum());

            /* 2‑1) IMAGE 테이블 insert / update 결정 */
            if (adminPlaningDAO.existsImage(imgNum) == 0)
                adminPlaningDAO.insertImage(imgDto);
            else
                adminPlaningDAO.updateImage(imgDto);
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
    
}
