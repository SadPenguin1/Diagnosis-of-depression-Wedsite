package com.linkin.dao;

import java.util.List;

import com.linkin.entity.SurveyHistory;
import com.linkin.model.SearchSurveyHistoryDTO;

public interface SurveyHistoryDao {
	
	void add(SurveyHistory surveyHistory);

	void delete(SurveyHistory surveyHistory);

	List<SurveyHistory> find(SearchSurveyHistoryDTO searchSurveyHistoryDTO);

	Long count(SearchSurveyHistoryDTO searchSurveyHistoryDTO);

	Long countTotal(SearchSurveyHistoryDTO searchSurveyHistoryDTO);
	
	SurveyHistory get(Long id);
}
