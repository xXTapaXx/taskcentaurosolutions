package com.centauro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lists")
public class ListModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5123445241064042496L;

	@Id
	@GeneratedValue
	private Integer id;

	private String list;
	
	private Integer sync;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public Integer getSync() {
		return sync;
	}

	public void setSync(Integer sync) {
		this.sync = sync;
	}
	
	
	
	

	
}
