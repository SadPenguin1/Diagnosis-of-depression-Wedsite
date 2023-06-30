
package com.linkin.dao;

import com.linkin.entity.Setting;

public interface SettingDao {
	
	void update(Setting setting);

	Setting getById(Long id);

}
