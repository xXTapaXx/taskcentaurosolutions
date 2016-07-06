package com.centauro.view;

import java.util.List;

import com.google.api.services.tasks.model.Task;

public class TaskView {
	
	 public String id;

	    public String title;

	    public String status;

	    public Boolean isNew;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Boolean getIsNew() {
			return isNew;
		}

		public void setIsNew(Boolean isNew) {
			this.isNew = isNew;
		}
	
	    
}
