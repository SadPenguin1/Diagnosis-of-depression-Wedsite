package com.linkin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.CategoryDTO;
import com.linkin.model.SearchCategoryDTO;
import com.linkin.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryAPIController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/member/categories")
	public List<CategoryDTO> getAll() {
		SearchCategoryDTO searchCategoryDTO = new SearchCategoryDTO();
		searchCategoryDTO.setStart(null);
		return categoryService.findCategory(searchCategoryDTO);
	}

}
