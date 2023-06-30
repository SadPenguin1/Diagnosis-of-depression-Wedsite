package com.linkin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.QuestionDTO;
import com.linkin.model.SearchQuestionDTO;
import com.linkin.service.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionAPIController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/member/questions")
	public List<QuestionDTO> getAll() {
		SearchQuestionDTO searchQuestionDTO = new SearchQuestionDTO();
		searchQuestionDTO.setStart(null);
		return questionService.findQuestion(searchQuestionDTO);
	}

}
