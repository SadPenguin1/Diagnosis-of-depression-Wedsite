package com.linkin.service;

import com.linkin.model.AnswerDTO;

public interface AnswerService {

	void addAnswer(AnswerDTO answerDTO);

	void updateAnswer(AnswerDTO answerDTO);
	
	void deleteAnswer(Long id);
	
	AnswerDTO getAnswerByID(Long id);
	
}
