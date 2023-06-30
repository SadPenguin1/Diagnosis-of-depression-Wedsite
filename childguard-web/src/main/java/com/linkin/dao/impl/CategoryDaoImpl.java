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

import com.linkin.dao.CategoryDao;
import com.linkin.entity.Category;
import com.linkin.model.SearchCategoryDTO;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void addCategory(Category category) {
		entityManager.persist(category);
	}

	@Override
	public void updateCategory(Category category) {
		entityManager.merge(category);
	}

	@Override
	public void deleteCategory(Category category) {
		entityManager.remove(category);
	}

	@Override
	public List<Category> findCategory(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
					"%" + searchCategoryDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate1);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "title")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("title")));
			}
		} else if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "createdDate")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "id")) {
			if (searchCategoryDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
		}

		TypedQuery<Category> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchCategoryDTO.getStart() != null) {
			typedQuery.setFirstResult((searchCategoryDTO.getStart()));
			typedQuery.setMaxResults(searchCategoryDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long countCategory(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
					"%" + searchCategoryDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate1));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotalCategory(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Category> root = criteriaQuery.from(Category.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Category getCategoryByID(Long id) {
		return entityManager.find(Category.class, id);
	}

}
