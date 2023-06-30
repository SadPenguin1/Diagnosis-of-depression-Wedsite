package com.linkin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.dao.QuestionDao;
import com.linkin.entity.Question;
import com.linkin.model.SearchQuestionDTO;

@Repository
@Transactional
public class QuestionDaoImpl implements QuestionDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void addQuestion(Question question) {
		entityManager.persist(question);
	}

	@Override
	public void updateQuestion(Question question) {
		entityManager.merge(question);
	}

	@Override
	public void deleteQuestion(Question question) {
		entityManager.remove(question);
	}

	@Override
	public List<Question> findQuestion(SearchQuestionDTO searchQuestionDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
		Root<Question> root = criteriaQuery.from(Question.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchQuestionDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
					"%" + searchQuestionDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate1);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchQuestionDTO.getSortBy().getData(), "code")) {
			if (searchQuestionDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("code")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("code")));
			}
		} else if (StringUtils.equals(searchQuestionDTO.getSortBy().getData(), "createdDate")) {
			if (searchQuestionDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchQuestionDTO.getSortBy().getData(), "id")) {
			if (searchQuestionDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
		}

		TypedQuery<Question> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchQuestionDTO.getStart() != null) {
			typedQuery.setFirstResult((searchQuestionDTO.getStart()));
			typedQuery.setMaxResults(searchQuestionDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long countQuestion(SearchQuestionDTO searchQuestionDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Question> root = criteriaQuery.from(Question.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchQuestionDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
					"%" + searchQuestionDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate1));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotalQuestion(SearchQuestionDTO searchQuestionDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Question> root = criteriaQuery.from(Question.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Question getQuestionByID(Long id) {
		return entityManager.find(Question.class, id);
	}

}
