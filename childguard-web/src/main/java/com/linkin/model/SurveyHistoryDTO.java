package com.linkin.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class SurveyHistoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String code;
	private String question;
	private String answer;
	private String createdDate;
//	private UserDTO createdBy;
	private String username;
	private String password;
	private String phone;
	private Integer score;


	public SurveyHistoryDTO() {
		super();
	}

	public SurveyHistoryDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

//	public UserDTO getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(UserDTO createdBy) {
//		this.createdBy = createdBy;
//	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
