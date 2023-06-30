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

import com.linkin.dao.ScoreMappingDao;
import com.linkin.entity.ScoreMapping;
import com.linkin.model.SearchScoreMappingDTO;

@Repository
@Transactional
public class ScoreMappingDaoImpl implements ScoreMappingDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void addScoreMapping(ScoreMapping scoreMapping) {
		entityManager.persist(scoreMapping);
	}

	@Override
	public void updateScoreMapping(ScoreMapping scoreMapping) {
		entityManager.merge(scoreMapping);
	}

	@Override
	public void deleteScoreMapping(ScoreMapping scoreMapping) {
		entityManager.remove(scoreMapping);
	}

	@Override
	public List<ScoreMapping> findScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ScoreMapping> criteriaQuery = criteriaBuilder.createQuery(ScoreMapping.class);
		Root<ScoreMapping> root = criteriaQuery.from(ScoreMapping.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchScoreMappingDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
					"%" + searchScoreMappingDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate1);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchScoreMappingDTO.getSortBy().getData(), "min")) {
			if (searchScoreMappingDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("min")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("min")));
			}
		} else if (StringUtils.equals(searchScoreMappingDTO.getSortBy().getData(), "createdDate")) {
			if (searchScoreMappingDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchScoreMappingDTO.getSortBy().getData(), "id")) {
			if (searchScoreMappingDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
		}

		TypedQuery<ScoreMapping> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchScoreMappingDTO.getStart() != null) {
			typedQuery.setFirstResult((searchScoreMappingDTO.getStart()));
			typedQuery.setMaxResults(searchScoreMappingDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long countScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<ScoreMapping> root = criteriaQuery.from(ScoreMapping.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchScoreMappingDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
					"%" + searchScoreMappingDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate1));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotalScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<ScoreMapping> root = criteriaQuery.from(ScoreMapping.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public ScoreMapping getScoreMappingByID(Long id) {
		return entityManager.find(ScoreMapping.class, id);
	}

}
