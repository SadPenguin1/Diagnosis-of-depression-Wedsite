package com.linkin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.SearchSurveyHistoryDTO;
import com.linkin.model.SurveyHistoryDTO;
import com.linkin.service.SurveyHistoryService;

@RestController
@RequestMapping("/api")
public class SurveyAPIController {

	@Autowired
	private SurveyHistoryService surveyHistoryService;


	@PostMapping("/member/surveys/add")
	public void addUser(@RequestBody List<SurveyHistoryDTO> surveyHistoryDTOs) {
		for (SurveyHistoryDTO surveyHistoryDTO : surveyHistoryDTOs) {
			try {
				surveyHistoryService.add(surveyHistoryDTO);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	@PostMapping(value = "/member/surveys")
	public List<SurveyHistoryDTO> finds(@RequestBody SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		return surveyHistoryService.finds(searchSurveyHistoryDTO);
	}
}
