package com.centauro.view;

import java.util.List;

import com.google.api.services.tasks.model.Task;

public class TaskView {
	
	public String id = null;
	public String title = null;
	public List<Task> tasks = null;
	public String getTitle() {
		return title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Task> getItems() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
