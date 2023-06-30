package com.linkin.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.SettingDTO;
import com.linkin.service.SettingService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class SettingAPIController {
	@Autowired
	private SettingService settingService;


	@PutMapping(value = "/admin/setting/update")
	public void update(@RequestBody SettingDTO settingDTO) {
		settingDTO.setId(1L);
		settingService.update(settingDTO);
	}

	@GetMapping(value = "/member/setting/{id}")
	public SettingDTO get(@PathVariable(name = "id") Long id) {
		return settingService.getById(id);
	}

}
