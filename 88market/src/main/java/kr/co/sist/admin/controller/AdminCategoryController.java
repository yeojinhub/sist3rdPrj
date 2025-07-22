package kr.co.sist.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    	try{
    		List<CategoryDTO> list = categoryService.findAll();
    		model.addAttribute("categories", list);
    	}catch(Exception e) {
    		//model.addAttribute("categories", Collections.emptyList());
    		model.addAttribute("errorMessage", "카테고리를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
    	}
    	model.addAttribute("categoryDTO", new CategoryDTO());
        return "admin/category/categoryList";
    }
	
    // 카테고리 등록
    @PostMapping
    public String create( CategoryDTO dto, RedirectAttributes ra) {
        try {
            categoryService.create(dto);
            ra.addFlashAttribute("successMessage", "새 카테고리가 등록되었습니다.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMessage", "이미 존재하는 카테고리 이름입니다: " + dto.getCatNum() + dto.getName());
        } catch (DuplicateKeyException e) {
            ra.addFlashAttribute("errorMessage", "이미 존재하는 카테고리 번호입니다: " + dto.getCatNum());
        } catch (DataAccessException e) {
            ra.addFlashAttribute("errorMessage", "카테고리 등록 중 데이터베이스 오류가 발생했습니다.");
        }
        return "redirect:/admin/category/categoryList";
    }

    // 카테고리 수정
    @PutMapping("/{catNum}")
    public String update(@PathVariable int catNum, CategoryDTO dto,RedirectAttributes ra) {
        dto.setCatNum(catNum);
        try {
            categoryService.update(dto);
            ra.addFlashAttribute("successMessage", "카테고리가 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMessage", "이미 존재하는 카테고리 이름입니다: " + dto.getCatNum()+ dto.getName());
        } catch (DataAccessException e) {
            ra.addFlashAttribute("errorMessage", "카테고리 수정 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/category/categoryList";
    }

    // 카테고리 삭제
    @DeleteMapping("/{catNum}")
    public String delete(@PathVariable int catNum,RedirectAttributes ra) {
        try {
            categoryService.delete(catNum);
            ra.addFlashAttribute("successMessage", "카테고리가 삭제되었습니다.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("errorMessage", "해당 카테고리는 다른 엔티티에서 참조 중이어서 삭제할 수 없습니다.");
        } catch (DataAccessException e) {
            ra.addFlashAttribute("errorMessage", "카테고리 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/category/categoryList";
    }
}
