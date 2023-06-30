package com.linkin.dao.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.dao.AnswerDao;
import com.linkin.entity.Answer;

@Repository
@Transactional
public class AnswerDaoImpl implements AnswerDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void addAnswer(Answer answer) {
		entityManager.persist(answer);
	}

	@Override
	public void updateAnswer(Answer answer) {
		entityManager.merge(answer);
	}

	@Override
	public void deleteAnswer(Answer answer) {
		entityManager.remove(answer);
	}

	@Override
	public Answer getAnswerByID(Long id) {
		return entityManager.find(Answer.class, id);
	}

}
