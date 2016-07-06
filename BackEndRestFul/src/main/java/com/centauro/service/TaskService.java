package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.TaskModel;


public interface TaskService {
	
	public TaskModel create(TaskModel task);
	public TaskModel delete(int id) throws ShopNotFound;
	public List<TaskModel> findAll();
	public TaskModel update(TaskModel task) throws ShopNotFound;
	public TaskModel findById(int id);
	
}
