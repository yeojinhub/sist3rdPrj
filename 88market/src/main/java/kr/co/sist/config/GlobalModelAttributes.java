package kr.co.sist.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.user.Service.CategoryService;

@ControllerAdvice
public class GlobalModelAttributes {
	
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute("categories")
	public List<CategoryDTO> populateCtegories(){
		return categoryService.getAllCategories();
	}

}
