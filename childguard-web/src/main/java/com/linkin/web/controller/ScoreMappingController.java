package com.linkin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.ResponseDTO;
import com.linkin.model.ScoreMappingDTO;
import com.linkin.model.SearchScoreMappingDTO;
import com.linkin.service.ScoreMappingService;

@Controller
@RequestMapping("/admin")
public class ScoreMappingController {

	@Autowired
	private ScoreMappingService scoreMappingService;

	@GetMapping("/score-mappings")
	public String listScoreMapping(Model model) {
		return "admin/score-mapping/list-score-mappings";
	}

	@PostMapping("/score-mappings")
	public ResponseEntity<ResponseDTO<ScoreMappingDTO>> findScoreMapping(
			@RequestBody SearchScoreMappingDTO searchScoreMappingDTO) {
		ResponseDTO<ScoreMappingDTO> responseDTO = new ResponseDTO<ScoreMappingDTO>();
		responseDTO.setRecordsTotal(scoreMappingService.countTotalScoreMapping(searchScoreMappingDTO));
		responseDTO.setRecordsFiltered(scoreMappingService.countScoreMapping(searchScoreMappingDTO));
		responseDTO.setData(scoreMappingService.findScoreMapping(searchScoreMappingDTO));

		return new ResponseEntity<ResponseDTO<ScoreMappingDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/score-mapping/delete/{id}")
	public ResponseEntity<String> deleteScoreMapping(@PathVariable(name = "id") Long id) {
		scoreMappingService.deleteScoreMapping(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/score-mapping/delete-multi/{ids}")
	public ResponseEntity<String> deleteMulti(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				scoreMappingService.deleteScoreMapping(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/score-mapping/add")
	public @ResponseBody ScoreMappingDTO addScoreMapping(@RequestBody ScoreMappingDTO scoreMappingDTO) {
		scoreMappingService.addScoreMapping(scoreMappingDTO);
		return scoreMappingDTO;
	}

	@PutMapping("/score-mapping/update")
	public @ResponseBody ScoreMappingDTO updateScoreMapping(@RequestBody ScoreMappingDTO scoreMappingDTO) {
		scoreMappingService.updateScoreMapping(scoreMappingDTO);
		return scoreMappingDTO;
	}
}
