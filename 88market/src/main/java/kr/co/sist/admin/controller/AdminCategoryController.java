package kr.co.sist.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.user.Service.CategoryService;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/*
	 * public List<CategoryDTO> getAll(){
	 * 
	 * return categoryService.findAll(); }
	 */

}
