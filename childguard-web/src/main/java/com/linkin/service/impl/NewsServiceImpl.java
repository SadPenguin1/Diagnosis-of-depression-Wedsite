package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.NewsDao;
import com.linkin.entity.Category;
import com.linkin.entity.News;
import com.linkin.entity.Question;
import com.linkin.model.NewsDTO;
import com.linkin.model.SearchNewsDTO;
import com.linkin.service.NewsService;
import com.linkin.utils.DateTimeUtils;
import com.linkin.utils.SMSMessage;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsDao newsDao;
	
	

	@Override
	public void addNews(NewsDTO newsDTO) {
		News news = new News();
		news.setTitle(newsDTO.getTitle());
		news.setContent(newsDTO.getContent());
		news.setCategory(new Category(newsDTO.getCategoryId()));
		
		newsDao.addNews(news);
		newsDTO.setId(news.getId());

	}
	@Override
	public void updateNews(NewsDTO newsDTO) {
		News news = newsDao.getNewsByID(newsDTO.getId());
		if (news != null) {
			news.setTitle(newsDTO.getTitle());
			news.setContent(newsDTO.getContent());

			newsDao.updateNews(news);
		}
	}

	@Override
	public void deleteNews(Long id) {
		News news = newsDao.getNewsByID(id);
		if (news != null) {
			newsDao.deleteNews(news);
		}
	}

	@Override
	public List<NewsDTO> findNews(SearchNewsDTO searchNewsDTO) {
		List<News> newss = newsDao.findNews(searchNewsDTO);
		List<NewsDTO> newsDTOs = new ArrayList<NewsDTO>();
		newss.forEach(news -> {
			NewsDTO newsDTO = new NewsDTO();
			newsDTO.setId(news.getId());
			newsDTO.setTitle(news.getTitle());
			newsDTO.setContent(news.getContent());
			newsDTO.setCreatedDate(DateTimeUtils.formatDate(news.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			newsDTO.setCategoryId(news.getCategory().getId()); 
			newsDTOs.add(newsDTO);
		});
		return newsDTOs;
	}

	@Override
	public List<NewsDTO> getNewsByCategoryId(Long categoryId) {
	    List<News> newsList = newsDao.getNewsByCategoryId(categoryId);
	    List<NewsDTO> newsDTOList = new ArrayList<>();

	    for (News news : newsList) {
	        NewsDTO newsDTO = new NewsDTO();
	        newsDTO.setId(news.getId());
	        newsDTO.setTitle(news.getTitle());
	        newsDTO.setContent(news.getContent());
	        newsDTO.setCreatedDate(DateTimeUtils.formatDate(news.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));

	        newsDTOList.add(newsDTO);
	    }

	    return newsDTOList;
	}
	@Override
	public Long countNews(SearchNewsDTO searchNewsDTO) {
		return newsDao.countNews(searchNewsDTO);
	}

	@Override
	public Long countTotalNews(SearchNewsDTO searchNewsDTO) {
		return newsDao.countTotalNews(searchNewsDTO);
	}

	@Override
	public NewsDTO getNewsByID(Long id) {
		News news = newsDao.getNewsByID(id);
		if (news != null) {
			NewsDTO newsDTO = new NewsDTO();
			newsDTO.setId(news.getId());
			newsDTO.setTitle(news.getTitle());
			newsDTO.setContent(news.getContent());
			newsDTO.setCreatedDate(DateTimeUtils.formatDate(news.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));

			return newsDTO;
		}
		return null;
	}

}
