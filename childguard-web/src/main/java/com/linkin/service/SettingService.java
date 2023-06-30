package com.linkin.service;

import com.linkin.model.SettingDTO;

public interface SettingService {
	void update(SettingDTO settingDTO);

    SettingDTO getById(Long id);

}
