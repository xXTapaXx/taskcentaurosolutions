package com.centauro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -770967247547799670L;

	@Id
	@GeneratedValue
	private Integer id;

	private String task;

	private Integer task_list_id;
	
	private String status;
	
	private Integer sync;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Integer getTask_list_id() {
		return task_list_id;
	}

	public void setTask_list_id(Integer task_list_id) {
		this.task_list_id = task_list_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSync() {
		return sync;
	}

	public void setSync(Integer sync) {
		this.sync = sync;
	}
	
	
	
	
}
