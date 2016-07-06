package com.centauro.model;

import java.io.Serializable;

import javax.persistence.Id;

public class SharedId implements Serializable{
	
	private String email;
	
	private Integer shared_task_id;

	private Integer shared_list_id;
}
