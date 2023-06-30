package com.linkin.model;

import lombok.Data;

@Data
public class SearchSurveyHistoryDTO extends SearchDTO {
	private static final long serialVersionUID = 1L;

//	private Long userId;
	private String username;
	private String phone;
	private String fromDate;
	private String toDate;

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
