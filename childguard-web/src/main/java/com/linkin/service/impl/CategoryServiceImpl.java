package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.CategoryDao;
import com.linkin.entity.Category;
import com.linkin.entity.News;
import com.linkin.model.CategoryDTO;
import com.linkin.model.NewsDTO;
import com.linkin.model.SearchCategoryDTO;
import com.linkin.service.CategoryService;
import com.linkin.utils.DateTimeUtils;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public void addCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setTitle(categoryDTO.getTitle());
		

		categoryDao.addCategory(category);
		categoryDTO.setId(category.getId());
	}

	@Override
	public void updateCategory(CategoryDTO categoryDTO) {
		Category category = categoryDao.getCategoryByID(categoryDTO.getId());
		System.out.println( categoryDao.getCategoryByID(categoryDTO.getId()));
		System.out.println("1111111111111");
		if (category != null) {
			System.out.println("00000000");
			category.setTitle(categoryDTO.getTitle());
		
			categoryDao.updateCategory(category);
		}

	}

	@Override
	public void deleteCategory(Long id) {
		Category category = categoryDao.getCategoryByID(id);
		if (category != null) {
			categoryDao.deleteCategory(category);
		}
	}

	@Override
	public List<CategoryDTO> findCategory(SearchCategoryDTO searchCategoryDTO) {
		List<Category> categorys = categoryDao.findCategory(searchCategoryDTO);
		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		categorys.forEach(category -> {
			categoryDTOs.add(convertToDTO(category));
		});
		return categoryDTOs;
	}

	@Override
	public Long countCategory(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.countCategory(searchCategoryDTO);
	}

	@Override
	public Long countTotalCategory(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.countTotalCategory(searchCategoryDTO);
	}

	@Override
	public CategoryDTO getCategoryByID(Long id) {
		Category category = categoryDao.getCategoryByID(id);
		if (category != null) {
			return convertToDTO(category);
		}
		return null;
	}

	private CategoryDTO convertToDTO(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setTitle(category.getTitle());
		categoryDTO.setCreatedDate(DateTimeUtils.formatDate(category.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));

		List<NewsDTO> listNewsDTO = new ArrayList<NewsDTO>();
		for (News news : category.getListNews()) {
			NewsDTO newsDTO = new NewsDTO();
			newsDTO.setId(news.getId());
			newsDTO.setTitle(news.getTitle());
			newsDTO.setContent(news.getContent());
			newsDTO.setCategoryId(category.getId());

			listNewsDTO.add(newsDTO);
		}
		categoryDTO.setListNewsDTO(listNewsDTO);

		return categoryDTO;
	}

}
