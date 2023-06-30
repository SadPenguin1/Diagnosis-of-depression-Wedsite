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

import com.linkin.dao.SurveyHistoryDao;
import com.linkin.entity.SurveyHistory;
import com.linkin.model.SearchSurveyHistoryDTO;
import com.linkin.utils.DateTimeUtils;

@Repository
@Transactional
public class SurveyHistoryDaoImpl implements SurveyHistoryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void add(SurveyHistory surveyHistory) {
		entityManager.persist(surveyHistory);
	}

	@Override
	public void delete(SurveyHistory surveyHistory) {
		entityManager.remove(surveyHistory);
	}

	@Override
	public List<SurveyHistory> find(SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SurveyHistory> criteriaQuery = criteriaBuilder.createQuery(SurveyHistory.class);
		Root<SurveyHistory> root = criteriaQuery.from(SurveyHistory.class);
		/* Join<SurveyHistory, User> user = root.join("createdBy"); */

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("question")),
					"%" + searchSurveyHistoryDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate1);
		}

//		if (searchSurveyHistoryDTO.getUserId() != null) {
//			Predicate predicate1 = criteriaBuilder.equal(user.get("id"), searchSurveyHistoryDTO.getUserId());
//			predicates.add(predicate1);
//		}
//
//		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getPhone())) {
//			Predicate predicate1 = criteriaBuilder.like(user.get("phone"),
//					"%" + searchSurveyHistoryDTO.getPhone() + "%");
//			predicates.add(predicate1);
//		}

		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getFromDate())) {
			try {
				Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"),
						DateTimeUtils.getStartOfDay(DateTimeUtils.parseDate(searchSurveyHistoryDTO.getFromDate(),
								DateTimeUtils.DD_MM_YYYY)));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getToDate())) {
			try {
				Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"),
						DateTimeUtils.getEndOfDay(
								DateTimeUtils.parseDate(searchSurveyHistoryDTO.getToDate(), DateTimeUtils.DD_MM_YYYY)));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchSurveyHistoryDTO.getSortBy().getData(), "createdDate")) {
			if (searchSurveyHistoryDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchSurveyHistoryDTO.getSortBy().getData(), "id")) {
			if (searchSurveyHistoryDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		}

		TypedQuery<SurveyHistory> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchSurveyHistoryDTO.getStart() != null) {
			typedQuery.setFirstResult((searchSurveyHistoryDTO.getStart()));
			typedQuery.setMaxResults(searchSurveyHistoryDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<SurveyHistory> root = criteriaQuery.from(SurveyHistory.class);
//		Join<SurveyHistory, User> user = root.join("createdBy");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getKeyword())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("question")),
					"%" + searchSurveyHistoryDTO.getKeyword().toLowerCase() + "%"));
		}
//
//		if (searchSurveyHistoryDTO.getUserId() != null) {
//			predicates.add(criteriaBuilder.equal(user.get("id"), searchSurveyHistoryDTO.getUserId()));
//		}

//		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getPhone())) {
//			Predicate predicate1 = criteriaBuilder.like(user.get("phone"), searchSurveyHistoryDTO.getPhone());
//			predicates.add(predicate1);
//		}

		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getFromDate())) {
			try {
				Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"),
						DateTimeUtils.getStartOfDay(DateTimeUtils.parseDate(searchSurveyHistoryDTO.getFromDate(),
								DateTimeUtils.DD_MM_YYYY)));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSurveyHistoryDTO.getToDate())) {
			try {
				Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"),
						DateTimeUtils.getEndOfDay(
								DateTimeUtils.parseDate(searchSurveyHistoryDTO.getToDate(), DateTimeUtils.DD_MM_YYYY)));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<SurveyHistory> root = criteriaQuery.from(SurveyHistory.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public SurveyHistory get(Long id) {
		return entityManager.find(SurveyHistory.class, id);
	}

}
