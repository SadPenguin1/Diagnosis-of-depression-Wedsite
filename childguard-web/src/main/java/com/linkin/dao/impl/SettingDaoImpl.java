package com.linkin.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linkin.dao.SettingDao;
import com.linkin.entity.Setting;

@Transactional
@Repository
public class SettingDaoImpl implements SettingDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void update(Setting setting) {
		entityManager.merge(setting);

	}

	@Override
	public Setting getById(Long id) {
		return entityManager.find(Setting.class, id);
	}

}
