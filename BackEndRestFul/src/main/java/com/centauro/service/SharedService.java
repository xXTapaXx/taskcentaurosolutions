package com.centauro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ListModel;
import com.centauro.model.SharedModel;
import com.centauro.model.TaskModel;


public interface SharedService {
	
	public SharedModel create(SharedModel shared);
	public List<SharedModel> findAll();
	public SharedModel update(SharedModel shared) throws ShopNotFound;
	public SharedModel findByEmailAndSharedTask(String email,TaskModel shared_task_id);
	public List<SharedModel> findByListId(String list_id);
	public SharedModel findByTaskId(String findBySharedListId);
	public List<SharedModel> findBySharedListId(ListModel shared_list_id);
	public List<SharedModel> getSync(String email);
	public List<SharedModel> updateSharedSync(ListModel shared_task_id, String email);
}
