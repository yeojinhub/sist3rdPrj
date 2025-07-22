package kr.co.sist.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.DTO.CategoryDTO;
import kr.co.sist.user.Service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	// 전체 조회
    @GetMapping("/categoryList")
    public String categoryListPage(Model model) {
        List<CategoryDTO> list = categoryService.findAll();
        model.addAttribute("categories", list);
        model.addAttribute("categoryDTO", new CategoryDTO());
        return "admin/category/categoryList";
    }
	
    // 카테고리 등록
    @PostMapping
    public String create( CategoryDTO dto) {
        categoryService.create(dto);
        return "redirect:/admin/category/categoryList";
    }

    // 카테고리 수정
    @PutMapping("/{catNum}")
    public String update(@PathVariable int catNum, CategoryDTO dto) {
        dto.setCatNum(catNum);
        categoryService.update(dto);
        return "redirect:/admin/category/categoryList";
    }

    // 카테고리 삭제
    @DeleteMapping("/{catNum}")
    public String delete(@PathVariable int catNum) {
        categoryService.delete(catNum);
        return "redirect:/admin/category/categoryList";
    }
	 

}
