package com.linkin.web.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.QuestionDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchQuestionDTO;
import com.linkin.model.SearchSurveyHistoryDTO;
import com.linkin.model.SurveyHistoryDTO;
import com.linkin.service.QuestionService;
import com.linkin.service.SurveyHistoryService;

@Controller
@RequestMapping(value = "/admin")
public class SurveyHistoryController extends BaseWebController {

	@Autowired
	private SurveyHistoryService surveyHistoryService;

	@Autowired
	private QuestionService questionService;

	@GetMapping("/survey-histories")
	public String listSim(Model model) {
		return "admin/surveyHistory/list-survey-history";
	}

	@PostMapping("/survey-histories")
	public ResponseEntity<ResponseDTO<SurveyHistoryDTO>> findSurveyHistory(
			@RequestBody SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		ResponseDTO<SurveyHistoryDTO> responseDTO = new ResponseDTO<SurveyHistoryDTO>();
		responseDTO.setRecordsTotal(surveyHistoryService.countTotal(searchSurveyHistoryDTO));
		responseDTO.setRecordsFiltered(surveyHistoryService.count(searchSurveyHistoryDTO));
		responseDTO.setData(surveyHistoryService.finds(searchSurveyHistoryDTO));

		return new ResponseEntity<ResponseDTO<SurveyHistoryDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/survey-history/delete/{id}")
	public ResponseEntity<String> deleteSurveyHistory(@PathVariable(name = "id") Long id) {
		surveyHistoryService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/survey-history/delete-multi/{ids}")
	public ResponseEntity<String> deleteSurveyHistory(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				surveyHistoryService.delete(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/survey-history/excel/export")
	public @ResponseBody String exportOwner(@RequestBody SearchSurveyHistoryDTO searchSurveyHistoryDTO) {
		searchSurveyHistoryDTO.setStart(null);
		Map<String, List<SurveyHistoryDTO>> map = surveyHistoryService.findForExcel(searchSurveyHistoryDTO);
		System.out.println("Create file survey-history excel");
		String fileName = "survey-history.xlsx";

		XSSFWorkbook workbook = new XSSFWorkbook();
		// add date to name of exel file
		XSSFSheet sheet = workbook.createSheet("khaosat");

		SearchQuestionDTO searchQuestionDTO = new SearchQuestionDTO();
		searchQuestionDTO.setStart(null);
		List<QuestionDTO> questionDTOs = questionService.findQuestion(searchQuestionDTO);

		int rowNum = 0;
		Row firstRow = sheet.createRow(rowNum++);

		firstRow.createCell(0).setCellValue("SDT");
		firstRow.createCell(1).setCellValue("Tên");
		firstRow.createCell(2).setCellValue("Điểm");
		int colHeadNum = 3;
		for (QuestionDTO questionDTO : questionDTOs) {
			firstRow.createCell(colHeadNum++).setCellValue(questionDTO.getCode() + "-" + questionDTO.getContent());
		}

		for (Entry<String, List<SurveyHistoryDTO>> entry : map.entrySet()) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(entry.getKey());

			/// sort by question
			List<SurveyHistoryDTO> surveys = entry.getValue();
			surveys.sort(new Comparator<SurveyHistoryDTO>() {
				@Override
				public int compare(SurveyHistoryDTO o1, SurveyHistoryDTO o2) {
					return (int) (o1.getId() - o2.getId());
				}
			});
			int colNum = 3;
			for (SurveyHistoryDTO surveyHistoryDTO : surveys) {
				row.createCell(colNum++).setCellValue(surveyHistoryDTO.getAnswer());
			}
			
			int diem =0;
			for (SurveyHistoryDTO surveyHistoryDTO : surveys) {
				diem += surveyHistoryDTO.getScore();	
			}
			row.createCell(2).setCellValue(diem);
			row.createCell(1).setCellValue(surveys.get(0).getUsername());
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		return fileName;
	}
}
