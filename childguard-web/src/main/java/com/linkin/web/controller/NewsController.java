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

import com.linkin.Repo.CategoryRepo;
import com.linkin.model.NewsDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchNewsDTO;
import com.linkin.service.NewsService;

@Controller
@RequestMapping(value = "/admin")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private CategoryRepo categoryRepo;

	@GetMapping("/list-news")
	public String listNews(Model model) {
		
		return "admin/news/list-news";
	}

	@PostMapping("/list-news")
	public ResponseEntity<ResponseDTO<NewsDTO>> findNews(@RequestBody SearchNewsDTO searchNewsDTO) {
		ResponseDTO<NewsDTO> responseDTO = new ResponseDTO<NewsDTO>();
		responseDTO.setRecordsTotal(newsService.countTotalNews(searchNewsDTO));
		responseDTO.setRecordsFiltered(newsService.countNews(searchNewsDTO));
		responseDTO.setData(newsService.findNews(searchNewsDTO));

		return new ResponseEntity<ResponseDTO<NewsDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/news/delete/{id}")
	public ResponseEntity<String> deleteNews(@PathVariable(name = "id") Long id) {
		newsService.deleteNews(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/news/delete-multi/{ids}")
	public ResponseEntity<String> deleteNews(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				newsService.deleteNews(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/news/add")
	public String addUser(Model model) {
		model.addAttribute("categories", categoryRepo.findAll());
		
		return "admin/news/add-news";
	}

	@PostMapping("/news/add")
	public @ResponseBody NewsDTO addNews(@RequestBody NewsDTO newsDTO) {
		newsService.addNews(newsDTO);
		return newsDTO;
	}

	@PutMapping("/news/update")
	public @ResponseBody NewsDTO updateNews(@RequestBody NewsDTO newsDTO) {
		newsService.updateNews(newsDTO);
		return newsDTO;
	}
}
