package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.SurveyHistoryDao;
import com.linkin.entity.SurveyHistory;
import com.linkin.model.SearchSurveyHistoryDTO;
import com.linkin.model.SurveyHistoryDTO;
import com.linkin.service.SurveyHistoryService;
import com.linkin.utils.DateTimeUtils;

@Service
@Transactional
public class SurveyHistoryServiceImpl implements SurveyHistoryService {

	@Autowired
	private SurveyHistoryDao surveyHistoryDao;

	@Override
	public void add(SurveyHistoryDTO surveyHistoryDTO) {
		SurveyHistory surveyHistory = new SurveyHistory();
		surveyHistory.setAnswer(surveyHistoryDTO.getAnswer());
		surveyHistory.setQuestion(surveyHistoryDTO.getQuestion());
		surveyHistory.setQuestionCode(surveyHistoryDTO.getCode());
		surveyHistory.setUsername(surveyHistoryDTO.getUsername());
		surveyHistory.setPhone(surveyHistoryDTO.getPhone());
		surveyHistory.setScore(surveyHistoryDTO.getScore());

		surveyHistoryDao.add(surveyHistory);
		surveyHistoryDTO.setId(surveyHistory.getId());
	}

	@Override
	public void delete(Long id) {
		SurveyHistory surveyHistory = surveyHistoryDao.get(id);
		if (surveyHistory != null) {
			surveyHistoryDao.delete(surveyHistory);
		}
	}

	@Override
	public List<SurveyHistoryDTO> finds(SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		List<SurveyHistory> surveyHistories = surveyHistoryDao.find(searchSurveyHistoryDTO);
		List<SurveyHistoryDTO> surveyHistoryDTOs = new ArrayList<SurveyHistoryDTO>();
		surveyHistories.forEach(surveyHistory -> {
			SurveyHistoryDTO surveyHistoryDTO = new SurveyHistoryDTO();
			surveyHistoryDTO.setId(surveyHistory.getId());
			surveyHistoryDTO.setAnswer(surveyHistory.getAnswer());
			surveyHistoryDTO.setQuestion(surveyHistory.getQuestion());
			surveyHistoryDTO.setCreatedDate(
					DateTimeUtils.formatDate(surveyHistory.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			surveyHistoryDTO.setCode(surveyHistory.getQuestionCode());
			surveyHistoryDTO.setUsername(surveyHistory.getUsername());
			surveyHistoryDTO.setPhone(surveyHistory.getPhone());
			surveyHistoryDTO.setScore(surveyHistory.getScore());

//			UserDTO createdBy = new UserDTO();
//			createdBy.setName(surveyHistory.getCreatedBy().getName());
//			createdBy.setPhone(surveyHistory.getCreatedBy().getPhone());
//			createdBy.setRoleId(surveyHistory.getCreatedBy().getRole().getId());
//			surveyHistoryDTO.setCreatedBy(createdBy);
			surveyHistoryDTOs.add(surveyHistoryDTO);
		});

		return surveyHistoryDTOs;
	}

	@Override
	public Map<String, List<SurveyHistoryDTO>> findForExcel(SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		Map<String, List<SurveyHistoryDTO>> map = new TreeMap<>();

		List<SurveyHistory> surveyHistories = surveyHistoryDao.find(searchSurveyHistoryDTO);

		surveyHistories.forEach(surveyHistory -> {
			SurveyHistoryDTO surveyHistoryDTO = new SurveyHistoryDTO();
			surveyHistoryDTO.setId(surveyHistory.getId());
			surveyHistoryDTO.setAnswer(surveyHistory.getAnswer());
			surveyHistoryDTO.setQuestion(surveyHistory.getQuestion());
			surveyHistoryDTO.setCreatedDate(
					DateTimeUtils.formatDate(surveyHistory.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			surveyHistoryDTO.setCode(surveyHistory.getQuestionCode());
			surveyHistoryDTO.setUsername(surveyHistory.getUsername());
			surveyHistoryDTO.setPhone(surveyHistory.getPhone());
			surveyHistoryDTO.setScore(surveyHistory.getScore());

			if (map.get(surveyHistoryDTO.getPhone()) == null) {
				List<SurveyHistoryDTO> surveyHistoryDTOs = new ArrayList<SurveyHistoryDTO>();
				surveyHistoryDTOs.add(surveyHistoryDTO);
				map.put(surveyHistoryDTO.getPhone(), surveyHistoryDTOs);
			} else {
				List<SurveyHistoryDTO> surveyHistoryDTOs = map.get(surveyHistoryDTO.getPhone());
				surveyHistoryDTOs.add(surveyHistoryDTO);
				map.put(surveyHistoryDTO.getPhone(), surveyHistoryDTOs);
			}
		});

		return map;
	}

	@Override
	public Long count(SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		return surveyHistoryDao.count(searchSurveyHistoryDTO);
	}

	@Override
	public Long countTotal(SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		return surveyHistoryDao.countTotal(searchSurveyHistoryDTO);
	}
}
