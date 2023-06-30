package com.linkin.service;

import java.util.List;

import com.linkin.entity.News;
import com.linkin.model.NewsDTO;
import com.linkin.model.SearchNewsDTO;

public interface NewsService {

	void addNews(NewsDTO newsDTO);

	void updateNews(NewsDTO newsDTO);
	
	void deleteNews(Long id);

	List<NewsDTO> findNews(SearchNewsDTO searchNewsDTO);
	
	List<NewsDTO> getNewsByCategoryId(Long categoryId);

	Long countNews(SearchNewsDTO searchNewsDTO);

	Long countTotalNews(SearchNewsDTO searchNewsDTO);
	
	NewsDTO getNewsByID(Long id);
	
}
