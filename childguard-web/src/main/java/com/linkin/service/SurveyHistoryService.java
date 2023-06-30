package com.linkin.service;

import java.util.List;
import java.util.Map;

import com.linkin.model.SearchSurveyHistoryDTO;
import com.linkin.model.SurveyHistoryDTO;

public interface SurveyHistoryService {
	void add(SurveyHistoryDTO surveyHistoryDTO);

	void delete(Long id);

	List<SurveyHistoryDTO> finds(SearchSurveyHistoryDTO searchSurveyHistoryDTO);

	Long count(SearchSurveyHistoryDTO searchSurveyHistoryDTO);

	Long countTotal(SearchSurveyHistoryDTO searchSurveyHistoryDTO);

	Map<String, List<SurveyHistoryDTO>> findForExcel(SearchSurveyHistoryDTO searchSurveyHistoryDTO);

}
