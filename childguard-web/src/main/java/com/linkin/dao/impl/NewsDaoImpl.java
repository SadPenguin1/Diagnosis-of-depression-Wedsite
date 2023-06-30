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
import javax.persistence.criteria.Join;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.dao.NewsDao;
import com.linkin.entity.News;
import com.linkin.entity.Category;

import com.linkin.model.SearchNewsDTO;

@Repository
@Transactional
public class NewsDaoImpl implements NewsDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void addNews(News news) {
		entityManager.persist(news);
	}

	@Override
	public void updateNews(News news) {
		entityManager.merge(news);
	}

	@Override
	public void deleteNews(News news) {
		entityManager.remove(news);
	}

	@Override
	public List<News> findNews(SearchNewsDTO searchNewsDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
		Root<News> root = criteriaQuery.from(News.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchNewsDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
					"%" + searchNewsDTO.getKeyword().toLowerCase() + "%");
			Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
					"%" + searchNewsDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate1, predicate2));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchNewsDTO.getSortBy().getData(), "createdDate")) {
			if (searchNewsDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchNewsDTO.getSortBy().getData(), "id")) {
			if (searchNewsDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		}

		TypedQuery<News> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchNewsDTO.getStart() != null) {
			typedQuery.setFirstResult((searchNewsDTO.getStart()));
			typedQuery.setMaxResults(searchNewsDTO.getLength());
		}
		return typedQuery.getResultList();
	}
	
	@Override
	public List<News> getNewsByCategoryId(Long categoryId) {
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
	    Root<News> root = criteriaQuery.from(News.class);
	    Join<News, Category> categoryJoin = root.join("category");

	    Predicate predicate = criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
	    criteriaQuery.where(predicate);

	    TypedQuery<News> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
	    return typedQuery.getResultList();
	}
	@Override
	public Long countNews(SearchNewsDTO searchNewsDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<News> root = criteriaQuery.from(News.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchNewsDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
					"%" + searchNewsDTO.getKeyword().toLowerCase() + "%");
			Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
					"%" + searchNewsDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate1, predicate2));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotalNews(SearchNewsDTO searchNewsDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<News> root = criteriaQuery.from(News.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public News getNewsByID(Long id) {
		return entityManager.find(News.class, id);
	}

}
