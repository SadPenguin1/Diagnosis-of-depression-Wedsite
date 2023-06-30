package com.linkin.service;

import java.util.List;

import com.linkin.model.ScoreMappingDTO;
import com.linkin.model.SearchScoreMappingDTO;

public interface ScoreMappingService {

	void addScoreMapping(ScoreMappingDTO scoreMappingDTO);

	void updateScoreMapping(ScoreMappingDTO scoreMappingDTO);

	void deleteScoreMapping(Long id);

	List<ScoreMappingDTO> findScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO);

	Long countScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO);

	Long countTotalScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO);

	ScoreMappingDTO getScoreMappingByID(Long id);

}
