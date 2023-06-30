package com.linkin.dao;

import java.util.List;

import com.linkin.entity.Question;
import com.linkin.model.SearchQuestionDTO;

public interface QuestionDao {

	void addQuestion(Question question);

	void updateQuestion(Question question);
	
	void deleteQuestion(Question question);

	List<Question> findQuestion(SearchQuestionDTO searchQuestionDTO);

	Long countQuestion(SearchQuestionDTO searchQuestionDTO);

	Long countTotalQuestion(SearchQuestionDTO searchQuestionDTO);
	
	Question getQuestionByID(Long id);
	
}
