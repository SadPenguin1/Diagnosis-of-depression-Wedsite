package com.linkin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "score_mapping")
@Data
@EqualsAndHashCode(callSuper = true)
public class ScoreMapping extends CreatedAuditable<User> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "min")
	private Integer min;

	@Column(name = "max")
	private Integer max;

	@Column(name = "description", length = 1000)
	private String description;


}
