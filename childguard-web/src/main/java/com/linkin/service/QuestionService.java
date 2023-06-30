package com.linkin.service;

import java.util.List;

import com.linkin.model.QuestionDTO;
import com.linkin.model.SearchQuestionDTO;

public interface QuestionService {

	void addQuestion(QuestionDTO questionDTO);

	void updateQuestion(QuestionDTO questionDTO);

	void deleteQuestion(Long id);

	List<QuestionDTO> findQuestion(SearchQuestionDTO searchQuestionDTO);

	Long countQuestion(SearchQuestionDTO searchQuestionDTO);

	Long countTotalQuestion(SearchQuestionDTO searchQuestionDTO);

	QuestionDTO getQuestionByID(Long id);

}
