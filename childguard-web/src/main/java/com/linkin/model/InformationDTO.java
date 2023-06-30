package com.linkin.model;

public class InformationDTO {

	private Long id;
	private Integer address;
	private Integer gender;
	private Integer physicalViolence;
	private Integer emotionalViolence;
	private Integer verbalViolence;
	private Integer sexualAbuse;
	private Integer grade;
	private String classz;

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getClassz() {
		return classz;
	}

	public void setClassz(String classz) {
		this.classz = classz;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getPhysicalViolence() {
		return physicalViolence;
	}

	public void setPhysicalViolence(Integer physicalViolence) {
		this.physicalViolence = physicalViolence;
	}

	public Integer getEmotionalViolence() {
		return emotionalViolence;
	}

	public void setEmotionalViolence(Integer emotionalViolence) {
		this.emotionalViolence = emotionalViolence;
	}

	public Integer getVerbalViolence() {
		return verbalViolence;
	}

	public void setVerbalViolence(Integer verbalViolence) {
		this.verbalViolence = verbalViolence;
	}

	public Integer getSexualAbuse() {
		return sexualAbuse;
	}

	public void setSexualAbuse(Integer sexualAbuse) {
		this.sexualAbuse = sexualAbuse;
	}

}
