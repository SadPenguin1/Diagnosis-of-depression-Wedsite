package com.linkin.model;

import java.io.Serializable;
import java.util.List;

public class QuestionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String content;
	private String code;
	private Integer questionType;
	private String createdDate;
	List<AnswerDTO> answerDTOs;

	public QuestionDTO() {
		super();
	}

	public QuestionDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<AnswerDTO> getAnswerDTOs() {
		return answerDTOs;
	}

	public void setAnswerDTOs(List<AnswerDTO> answerDTOs) {
		this.answerDTOs = answerDTOs;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
