package kr.co.sist.user.Service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.DAO.CategoryDAO;
import kr.co.sist.DTO.CategoryDTO;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    // 관리자
    // 카테고리 목록
    public List<CategoryDTO> findAll(){
    	return categoryDAO.findAll();
    }
	
    // 카테고리 등록
    public void create(CategoryDTO dto) {
        categoryDAO.insert(dto);
    }

    // 카테고리 수정
    public void update(CategoryDTO dto) {
        categoryDAO.update(dto);
    }

    //  카테고리 삭제
    public void delete(int catNum) {
        categoryDAO.delete(catNum);
    }
    
    
    // 사용자
    //전체 카테고리 가져오기
    public List<CategoryDTO> getAllCategories() {
        return categoryDAO.selectAllCategories();
    }//getAllCategories
    
    // 카테고리 이름 가져오기
    public String getCategoryName(int catNum) {
        CategoryDTO ctgDTO = categoryDAO.selectCategoryById(catNum);
        return ctgDTO != null ? ctgDTO.getName() : "";
    }//getCategoryName
    
    // 메인 - 카테고리 (키워드주목) 랜덤 가져오기
    public CategoryDTO getRandomCategory() {
        List<CategoryDTO> list = categoryDAO.selectAllCategories();
        if (list == null || list.isEmpty()) {
            return null;
        }
        int idx = new Random().nextInt(list.size());
        return list.get(idx);
    }//getRandomCategory
    
    
    
    
}