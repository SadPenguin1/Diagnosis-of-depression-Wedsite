package com.linkin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.CategoryDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchCategoryDTO;
import com.linkin.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public String listCategory(Model model) {
		return "admin/category/list-categories";
	}

	@PostMapping("/categories")
	public ResponseEntity<ResponseDTO<CategoryDTO>> findCategory(@RequestBody SearchCategoryDTO searchCategoryDTO) {
		ResponseDTO<CategoryDTO> responseDTO = new ResponseDTO<CategoryDTO>();
		responseDTO.setRecordsTotal(categoryService.countTotalCategory(searchCategoryDTO));
		responseDTO.setRecordsFiltered(categoryService.countCategory(searchCategoryDTO));
		responseDTO.setData(categoryService.findCategory(searchCategoryDTO));

		return new ResponseEntity<ResponseDTO<CategoryDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/category/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/category/delete-multi/{ids}")
	public ResponseEntity<String> deleteMulti(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				categoryService.deleteCategory(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/category/add")
	public @ResponseBody CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO) {
		categoryService.addCategory(categoryDTO);
		return categoryDTO;
	}

	@PutMapping("/category/update")
	public @ResponseBody CategoryDTO updateCategory(@RequestBody CategoryDTO categoryDTO) {
		categoryService.updateCategory(categoryDTO);
		return categoryDTO;
	}
}
