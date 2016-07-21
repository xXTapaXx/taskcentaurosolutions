package com.centauro.view;

import java.util.List;

import com.google.api.services.tasks.model.Task;

public class SyncSharedView {
	
	String nameList;
	
	Integer shared_list_id;
	
	Integer shared_task_id;
	
	TaskView task;

	public String getNameList() {
		return nameList;
	}

	public void setNameList(String nameList) {
		this.nameList = nameList;
	}

	public Integer getShared_list_id() {
		return shared_list_id;
	}

	public void setShared_list_id(Integer shared_list_id) {
		this.shared_list_id = shared_list_id;
	}

	public Integer getShared_task_id() {
		return shared_task_id;
	}

	public void setShared_task_id(Integer shared_task_id) {
		this.shared_task_id = shared_task_id;
	}

	public TaskView getTask() {
		return task;
	}

	public void setTask(TaskView task) {
		this.task = task;
	}
	 
	
	
    
	
}
