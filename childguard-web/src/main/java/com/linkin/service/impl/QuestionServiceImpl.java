package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.QuestionDao;
import com.linkin.entity.Answer;
import com.linkin.entity.Question;
import com.linkin.model.AnswerDTO;
import com.linkin.model.QuestionDTO;
import com.linkin.model.SearchQuestionDTO;
import com.linkin.service.QuestionService;
import com.linkin.utils.DateTimeUtils;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Override
	public void addQuestion(QuestionDTO questionDTO) {
		Question question = new Question();
		question.setContent(questionDTO.getContent());
		question.setQuestionType(questionDTO.getQuestionType());
		question.setCode(questionDTO.getCode());

		questionDao.addQuestion(question);
		questionDTO.setId(question.getId());
	}

	@Override
	public void updateQuestion(QuestionDTO questionDTO) {
		Question question = questionDao.getQuestionByID(questionDTO.getId());
		if (question != null) {
			question.setContent(questionDTO.getContent());
			question.setQuestionType(questionDTO.getQuestionType());
			question.setCode(questionDTO.getCode());

			questionDao.updateQuestion(question);
		}

	}

	@Override
	public void deleteQuestion(Long id) {
		Question question = questionDao.getQuestionByID(id);
		if (question != null) {
			questionDao.deleteQuestion(question);
		}
	}

	@Override
	public List<QuestionDTO> findQuestion(SearchQuestionDTO searchQuestionDTO) {
		List<Question> questions = questionDao.findQuestion(searchQuestionDTO);
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
		questions.forEach(question -> {
			questionDTOs.add(convertToDTO(question));
		});
		return questionDTOs;
	}

	@Override
	public Long countQuestion(SearchQuestionDTO searchQuestionDTO) {
		return questionDao.countQuestion(searchQuestionDTO);
	}

	@Override
	public Long countTotalQuestion(SearchQuestionDTO searchQuestionDTO) {
		return questionDao.countTotalQuestion(searchQuestionDTO);
	}

	@Override
	public QuestionDTO getQuestionByID(Long id) {
		Question question = questionDao.getQuestionByID(id);
		if (question != null) {
			return convertToDTO(question);
		}
		return null;
	}

	private QuestionDTO convertToDTO(Question question) {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setId(question.getId());
		questionDTO.setContent(question.getContent());
		questionDTO.setCode(question.getCode());
		questionDTO.setQuestionType(question.getQuestionType());
		questionDTO.setCreatedDate(DateTimeUtils.formatDate(question.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));

		List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
		for (Answer answer : question.getAnswers()) {
			AnswerDTO answerDTO = new AnswerDTO();
			answerDTO.setId(answer.getId());
			answerDTO.setContent(answer.getContent());
			answerDTO.setScore(answer.getScore());
			answerDTO.setQuestionId(question.getId());

			answerDTOs.add(answerDTO);
		}
		questionDTO.setAnswerDTOs(answerDTOs);

		return questionDTO;
	}

}
