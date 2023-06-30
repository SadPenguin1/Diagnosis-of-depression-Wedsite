package com.linkin.dao;

import java.util.List;

import com.linkin.entity.Category;
import com.linkin.model.SearchCategoryDTO;

public interface CategoryDao {

	void addCategory(Category category);

	void updateCategory(Category category);
	
	void deleteCategory(Category category);

	List<Category> findCategory(SearchCategoryDTO searchCategoryDTO);

	Long countCategory(SearchCategoryDTO searchCategoryDTO);

	Long countTotalCategory(SearchCategoryDTO searchCategoryDTO);
	
	Category getCategoryByID(Long id);
	
}
