package com.linkin.dao;

import java.util.List;

import com.linkin.entity.News;
import com.linkin.model.SearchNewsDTO;

public interface NewsDao {

	void addNews(News news);

	void updateNews(News news);
	
	void deleteNews(News news);

	List<News> findNews(SearchNewsDTO searchNewsDTO);
	
	List<News> getNewsByCategoryId(Long categoryId);
	
	Long countNews(SearchNewsDTO searchNewsDTO);

	Long countTotalNews(SearchNewsDTO searchNewsDTO);
	
	News getNewsByID(Long id);
	
}
