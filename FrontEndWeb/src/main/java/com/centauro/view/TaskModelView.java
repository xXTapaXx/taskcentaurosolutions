package com.centauro.view;

public class TaskModelView {
	
	public Integer id;

	public String task;

	public Integer task_list_id;
	
	public String status;
	
	public Integer sync;
	
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


	public TaskModelView(Integer id, String task, Integer task_list_id, String status, Integer sync) {
		super();
		this.id = id;
		this.task = task;
		this.task_list_id = task_list_id;
		this.status = status;
		this.sync = sync;
	}
}
