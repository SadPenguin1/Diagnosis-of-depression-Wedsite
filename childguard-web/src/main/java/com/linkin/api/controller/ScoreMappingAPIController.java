package com.linkin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.ScoreMappingDTO;
import com.linkin.model.SearchScoreMappingDTO;
import com.linkin.service.ScoreMappingService;

@RestController
@RequestMapping("/api")
public class ScoreMappingAPIController {

	@Autowired
	private ScoreMappingService scoreMappingService;

	@GetMapping("/member/score-mappings")
	public List<ScoreMappingDTO> getAll() {
		SearchScoreMappingDTO searchScoreMappingDTO = new SearchScoreMappingDTO();
		searchScoreMappingDTO.setStart(null);
		return scoreMappingService.findScoreMapping(searchScoreMappingDTO);
	}

}
