package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.ScoreMappingDao;
import com.linkin.entity.ScoreMapping;
import com.linkin.model.ScoreMappingDTO;
import com.linkin.model.SearchScoreMappingDTO;
import com.linkin.service.ScoreMappingService;
import com.linkin.utils.DateTimeUtils;

@Service
@Transactional
public class ScoreMappingServiceImpl implements ScoreMappingService {

	@Autowired
	private ScoreMappingDao scoreMappingDao;

	@Override
	public void addScoreMapping(ScoreMappingDTO scoreMappingDTO) {
		ScoreMapping scoreMapping = new ScoreMapping();
		scoreMapping.setMin(scoreMappingDTO.getMin());
		scoreMapping.setMax(scoreMappingDTO.getMax());
		scoreMapping.setDescription(scoreMappingDTO.getDescription());

		scoreMappingDao.addScoreMapping(scoreMapping);
		scoreMappingDTO.setId(scoreMapping.getId());
	}

	@Override
	public void updateScoreMapping(ScoreMappingDTO scoreMappingDTO) {
		ScoreMapping scoreMapping = scoreMappingDao.getScoreMappingByID(scoreMappingDTO.getId());
		if (scoreMapping != null) {
			scoreMapping.setMin(scoreMappingDTO.getMin());
			scoreMapping.setMax(scoreMappingDTO.getMax());
			scoreMapping.setDescription(scoreMappingDTO.getDescription());

			scoreMappingDao.updateScoreMapping(scoreMapping);
		}

	}

	@Override
	public void deleteScoreMapping(Long id) {
		ScoreMapping scoreMapping = scoreMappingDao.getScoreMappingByID(id);
		if (scoreMapping != null) {
			scoreMappingDao.deleteScoreMapping(scoreMapping);
		}
	}

	@Override
	public List<ScoreMappingDTO> findScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO) {
		List<ScoreMapping> scoreMappings = scoreMappingDao.findScoreMapping(searchScoreMappingDTO);
		List<ScoreMappingDTO> scoreMappingDTOs = new ArrayList<ScoreMappingDTO>();
		scoreMappings.forEach(scoreMapping -> {
			scoreMappingDTOs.add(convertToDTO(scoreMapping));
		});
		return scoreMappingDTOs;
	}

	@Override
	public Long countScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO) {
		return scoreMappingDao.countScoreMapping(searchScoreMappingDTO);
	}

	@Override
	public Long countTotalScoreMapping(SearchScoreMappingDTO searchScoreMappingDTO) {
		return scoreMappingDao.countTotalScoreMapping(searchScoreMappingDTO);
	}

	@Override
	public ScoreMappingDTO getScoreMappingByID(Long id) {
		ScoreMapping scoreMapping = scoreMappingDao.getScoreMappingByID(id);
		if (scoreMapping != null) {
			return convertToDTO(scoreMapping);
		}
		return null;
	}

	private ScoreMappingDTO convertToDTO(ScoreMapping scoreMapping) {
		ScoreMappingDTO scoreMappingDTO = new ScoreMappingDTO();
		scoreMappingDTO.setId(scoreMapping.getId());
		scoreMappingDTO.setMin(scoreMapping.getMin());
		scoreMappingDTO.setMax(scoreMapping.getMax());
		scoreMappingDTO.setDescription(scoreMapping.getDescription());
		scoreMappingDTO.setCreatedDate(DateTimeUtils.formatDate(scoreMapping.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));


		return scoreMappingDTO;
	}

}
