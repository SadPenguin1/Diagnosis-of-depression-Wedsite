package com.linkin.dao;

import com.linkin.entity.Answer;

public interface AnswerDao {

	void addAnswer(Answer answer);

	void updateAnswer(Answer answer);
	
	void deleteAnswer(Answer answer);

	Answer getAnswerByID(Long id);
	
}
