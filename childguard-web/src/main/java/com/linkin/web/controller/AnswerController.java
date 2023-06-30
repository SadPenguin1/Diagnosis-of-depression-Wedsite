package com.linkin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.AnswerDTO;
import com.linkin.service.AnswerService;

@Controller
@RequestMapping("/admin")
public class AnswerController {

	@Autowired
	private AnswerService answerService;
	
	@GetMapping("/answer/delete/{id}")
	public ResponseEntity<String> deleteAnswer(@PathVariable(name = "id") Long id) {
		answerService.deleteAnswer(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@PostMapping("/answer/add")
	public @ResponseBody AnswerDTO addAnswer(@RequestBody AnswerDTO answerDTO) {
		answerService.addAnswer(answerDTO);
		return answerDTO;
	}
	
	@PutMapping("/answer/update")
	public @ResponseBody AnswerDTO updateAnswer(@RequestBody AnswerDTO answerDTO) {
		answerService.updateAnswer(answerDTO);
		return answerDTO;
	}
	
}
