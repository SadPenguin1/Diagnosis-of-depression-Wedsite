package com.linkin.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.SettingDao;
import com.linkin.entity.Setting;
import com.linkin.model.SettingDTO;
import com.linkin.service.SettingService;

@Transactional
@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingDao settingDao;

	@Override
	public void update(SettingDTO settingDTO) {
		Setting setting = settingDao.getById(settingDTO.getId());
		if (setting != null) {
			setting.setHideSurvey(settingDTO.getHideSurvey());
			settingDao.update(setting);
		}
	}

	@Override
	public SettingDTO getById(Long id) {
		Setting setting = settingDao.getById(id);
		if (setting != null) {
			SettingDTO settingDTO = new SettingDTO();
			settingDTO.setId(setting.getId());
			settingDTO.setHideSurvey(setting.getHideSurvey());
			return settingDTO;
		}
		return null;
	}

}
