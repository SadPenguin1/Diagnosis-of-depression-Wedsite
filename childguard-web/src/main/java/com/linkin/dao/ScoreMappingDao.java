package com.linkin.dao;

import java.util.List;

import com.linkin.entity.ScoreMapping;
import com.linkin.model.SearchScoreMappingDTO;

public interface ScoreMappingDao {

	void addScoreMapping(ScoreMapping scoreMapping);

	void updateScoreMapping(ScoreMapping scoreMapping);
	
	void deleteScoreMapping(ScoreMapping scoreMapping);

	List<ScoreMapping> findScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO);

	Long countScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO);

	Long countTotalScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO);
	
	ScoreMapping getScoreMappingByID(Long id);
	
}
