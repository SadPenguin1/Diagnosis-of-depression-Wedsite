package com.linkin.model;

import java.io.Serializable;
import java.util.List;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	
	private String createdDate;
	List<NewsDTO> listNewsDTO;

	public CategoryDTO() {
		super();
	}

	public CategoryDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<NewsDTO> getListNewsDTO() {
		return listNewsDTO;
	}

	public void setListNewsDTO(List<NewsDTO> listNewsDTO) {
		this.listNewsDTO = listNewsDTO;
	}

	
}
