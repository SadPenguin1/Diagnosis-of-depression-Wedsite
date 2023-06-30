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

import com.linkin.model.QuestionDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchQuestionDTO;
import com.linkin.service.QuestionService;

@Controller
@RequestMapping("/admin")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/questions")
	public String listQuestion(Model model) {
		return "admin/question/list-questions";
	}

	@PostMapping("/questions")
	public ResponseEntity<ResponseDTO<QuestionDTO>> findQuestion(@RequestBody SearchQuestionDTO searchQuestionDTO) {
		ResponseDTO<QuestionDTO> responseDTO = new ResponseDTO<QuestionDTO>();
		responseDTO.setRecordsTotal(questionService.countTotalQuestion(searchQuestionDTO));
		responseDTO.setRecordsFiltered(questionService.countQuestion(searchQuestionDTO));
		responseDTO.setData(questionService.findQuestion(searchQuestionDTO));

		return new ResponseEntity<ResponseDTO<QuestionDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/question/delete/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable(name = "id") Long id) {
		questionService.deleteQuestion(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/question/delete-multi/{ids}")
	public ResponseEntity<String> deleteMulti(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				questionService.deleteQuestion(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/question/add")
	public @ResponseBody QuestionDTO addQuestion(@RequestBody QuestionDTO questionDTO) {
		questionService.addQuestion(questionDTO);
		return questionDTO;
	}

	@PutMapping("/question/update")
	public @ResponseBody QuestionDTO updateQuestion(@RequestBody QuestionDTO questionDTO) {
		questionService.updateQuestion(questionDTO);
		return questionDTO;
	}
}
