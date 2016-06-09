package com.centauro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menus")
public class MenuModel {
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="title")
	private String title;

	@Column(name="alias")
	private String alias;
	
	@Column(name="padre")
	private Integer father;
	
	@Column(name="son")
	private Integer son;

	public Integer getSon() {
		return son;
	}

	public void setSon(Integer son) {
		this.son = son;
	}

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

	public Integer getFather() {
		return father;
	}

	public void setFather(Integer father) {
		this.father = father;
	}

	
	
	
}
