package com.linkin.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class ScoreMappingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String description;
	private Integer min;
	private Integer max;
	private String createdDate;
	

	public ScoreMappingDTO() {
		super();
	}

	public ScoreMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
