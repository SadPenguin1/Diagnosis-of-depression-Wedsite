package com.linkin.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.AnswerDao;
import com.linkin.entity.Answer;
import com.linkin.entity.Question;
import com.linkin.model.AnswerDTO;
import com.linkin.service.AnswerService;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerDao answerDao;
	
	@Override
	public void addAnswer(AnswerDTO answerDTO) {
		Answer answer = new Answer();
		answer.setContent(answerDTO.getContent());
		answer.setQuestion(new Question(answerDTO.getQuestionId()));
		answer.setScore(answerDTO.getScore());
		
		answerDao.addAnswer(answer);
		answerDTO.setId(answer.getId());
	}

	@Override
	public void updateAnswer(AnswerDTO answerDTO) {
		Answer answer = answerDao.getAnswerByID(answerDTO.getId());
		if (answer!=null) {
			answer.setContent(answerDTO.getContent());
			answer.setScore(answerDTO.getScore());
			
			answerDao.updateAnswer(answer);
		}
	}

	@Override
	public void deleteAnswer(Long id) {
		Answer answer = answerDao.getAnswerByID(id);
		if (answer!=null) {
			answerDao.deleteAnswer(answer);
		}
	}

	@Override
	public AnswerDTO getAnswerByID(Long id) {
		Answer answer = answerDao.getAnswerByID(id);
		if (answer!=null) {
			AnswerDTO answerDTO = new AnswerDTO();
			answerDTO.setId(answer.getId());
			answerDTO.setContent(answer.getContent());
			answerDTO.setScore(answer.getScore());
			
			return answerDTO;
		}
		return null;
	}

}
