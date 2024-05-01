package com.pro.Controller;

import java.util.List;
import java.util.stream.Collectors;

//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pro.dto.Categorydto;
import com.pro.model.Category;
import com.pro.model.Product;
import com.pro.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/AddCategory")
	public Categorydto createCategory(@RequestBody Categorydto categoryDTO) {
		Category category = convertToCategory(categoryDTO);
		Category createdCategory = categoryService.createCategory(category);
		return convertToCategoryDTO(createdCategory);
	}

	@GetMapping
	public List<Categorydto> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return categories.stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
	}

	@GetMapping("/{categoryid}")
	public Categorydto getCategoryById(@PathVariable Long categoryid) {
		Category category = categoryService.getCategoryById(categoryid);
		return convertToCategoryDTO(category);
	}

	@PutMapping("/{categoryid}")
	public Categorydto updateCategory(@PathVariable Long categoryid, @RequestBody Categorydto categoryDTO) {
		Category category = convertToCategory(categoryDTO);
		Category updatedCategory = categoryService.updateCategory(categoryid, category);
		return convertToCategoryDTO(updatedCategory);
	}

	@DeleteMapping("/{categoryid}")
	public String deleteCategory(@PathVariable Long categoryid, @RequestBody Categorydto categoryDTO) {

		categoryService.deleteCategory(categoryid);
		return "Deleted Successfully....!";

	}

	private Categorydto convertToCategoryDTO(Category category) {
		Categorydto categoryDTO = new Categorydto();
		categoryDTO.setCategoryid(category.getCategoryid());
		categoryDTO.setName(category.getName());

		return categoryDTO;
	}

	private Category convertToCategory(Categorydto categoryDTO) {
		Category category = new Category();
		category.setCategoryid(categoryDTO.getCategoryid());
		category.setName(categoryDTO.getName());

		return category;
	}
}
