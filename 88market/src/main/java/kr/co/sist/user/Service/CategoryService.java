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

    public List<CategoryDTO> getAllCategories() {
        return categoryDAO.selectAllCategories();
    }//getAllCategories
    
    public String getCategoryName(int catNum) {
        CategoryDTO ctgDTO = categoryDAO.selectCategoryById(catNum);
        return ctgDTO != null ? ctgDTO.getName() : "";
    }//getCategoryName
    
    
    public CategoryDTO getRandomCategory() {
        List<CategoryDTO> list = categoryDAO.selectAllCategories();
        if (list == null || list.isEmpty()) {
            return null;
        }
        int idx = new Random().nextInt(list.size());
        return list.get(idx);
    }//getRandomCategory
    
}