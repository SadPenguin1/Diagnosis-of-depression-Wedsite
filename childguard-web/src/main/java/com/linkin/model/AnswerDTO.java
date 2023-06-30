package com.linkin.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class AnswerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String content;
	private String createdDate;
	private Long questionId;
	private int score;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(Long id) {
		super();
		this.id = id;
	}
}
