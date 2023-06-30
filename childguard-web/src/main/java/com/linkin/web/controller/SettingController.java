package com.linkin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class SettingController {
	
	@GetMapping("/setting")
	public String notelist() {
		return "admin/setting/setting";
	}
}
