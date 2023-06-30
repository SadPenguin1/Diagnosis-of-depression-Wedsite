package com.linkin.service;

import java.util.List;

import com.linkin.model.CategoryDTO;
import com.linkin.model.SearchCategoryDTO;

public interface CategoryService {

	void addCategory(CategoryDTO categoryDTO);

	void updateCategory(CategoryDTO categoryDTO);

	void deleteCategory(Long id);

	List<CategoryDTO> findCategory(SearchCategoryDTO searchCategoryDTO);

	Long countCategory(SearchCategoryDTO searchCategoryDTO);

	Long countTotalCategory(SearchCategoryDTO searchCategoryDTO);

	CategoryDTO getCategoryByID(Long id);

}
