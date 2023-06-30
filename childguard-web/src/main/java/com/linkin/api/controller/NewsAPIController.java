package com.linkin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.linkin.model.NewsDTO;
import com.linkin.model.SearchNewsDTO;
import com.linkin.service.NewsService;


@RestController
@RequestMapping("/api")
public class NewsAPIController {

	@Autowired
	private NewsService newsService;
	
	@GetMapping(value = "/member/news")
	public List<NewsDTO> getAll() {
		SearchNewsDTO searchNewsDTO = new SearchNewsDTO();
		searchNewsDTO.setStart(null);
		return newsService.findNews(searchNewsDTO);
	}


	@GetMapping("/category/{categoryId}")
	public List<NewsDTO> getNewsByCategoryId(@PathVariable Long categoryId) {
	    return newsService.getNewsByCategoryId(categoryId);
	}
     
}

