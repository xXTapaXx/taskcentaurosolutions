package com.centauro.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(SharedId.class)
@Table(name = "shared")
public class SharedModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5124381968199498327L;

	@Id
	private String email;
	
	@Id
	@ManyToOne()
    @JoinColumn(name="shared_task_id", referencedColumnName ="id")
	private TaskModel shared_task_id;
	
	@Id
	@ManyToOne()
    @JoinColumn(name="shared_list_id", referencedColumnName ="id")
	private ListModel shared_list_id;
	
	
	private String list_id;
	
	
	private String task_id;
	
	private Integer sync;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TaskModel getShared_task_id() {
		return shared_task_id;
	}

	public void setShared_task_id(TaskModel shared_task_id) {
		this.shared_task_id = shared_task_id;
	}

	public ListModel getShared_list_id() {
		return shared_list_id;
	}

	public void setShared_list_id(ListModel shared_list_id) {
		this.shared_list_id = shared_list_id;
	}

	public String getList_id() {
		return list_id;
	}

	public void setList_id(String list_id) {
		this.list_id = list_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public Integer getSync() {
		return sync;
	}

	public void setSync(Integer sync) {
		this.sync = sync;
	}
	
	
	
	
}
