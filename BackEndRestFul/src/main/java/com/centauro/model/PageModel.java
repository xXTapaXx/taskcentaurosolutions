package com.centauro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pages")
public class PageModel {
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="title")
	private String title;

	@Column(name="alias")
	private String alias;
	
	@Column(name="type")
	private String type;
	
	@Column(name="id_type")
	private Integer id_type;
	
	@Column(name="position")
	private String  position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId_type() {
		return id_type;
	}

	public void setId_type(Integer id_type) {
		this.id_type = id_type;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}


	
	
	
	
}
