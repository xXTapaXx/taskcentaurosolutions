package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.TaskModel;
import com.centauro.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Resource
	private TaskRepository taskRepository;

	@Override
	@Transactional
	public TaskModel create(TaskModel taskModel) {
		TaskModel createdTask = taskModel;
		return taskRepository.save(createdTask);
	}
	

	@Override
	@Transactional
	public TaskModel findById(int id) {
		return taskRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public TaskModel delete(int id) throws ShopNotFound {
		TaskModel deleteTask = taskRepository.findOne(id);
		
		if (deleteTask == null)
			throw new ShopNotFound();
		
		taskRepository.delete(deleteTask);
		return deleteTask;
	}
	
	

	@Override
	@Transactional
	public List<TaskModel> findAll() {
		return taskRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public TaskModel update(TaskModel taskModel) throws ShopNotFound {
		TaskModel updateTask = taskRepository.findOne(taskModel.getId());
		
		if (updateTask == null)
			throw new ShopNotFound();
		
		updateTask.setTask(updateTask.getTask());
		updateTask.setTask_list_id(updateTask.getTask_list_id());
		updateTask.setStatus(updateTask.getStatus());
		
		return updateTask;
	}

	

}
