package com.centauro.view;

import java.util.List;

import com.google.api.services.tasks.model.Task;

public class SharedView {
	
	String myEmail;
	
	List<String> emails;

    TaskListView taskListView;

	public String getMyEmail() {
		return myEmail;
	}

	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public TaskListView getTaskListView() {
		return taskListView;
	}

	public void setTaskListView(TaskListView taskListView) {
		this.taskListView = taskListView;
	}
    
    
	
}
