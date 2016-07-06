package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ListModel;
import com.centauro.model.SharedModel;
import com.centauro.model.TaskModel;
import com.centauro.repository.SharedRepository;

@Service
public class SharedServiceImpl implements SharedService {
	
	@Resource
	private SharedRepository sharedRepository;

	@Override
	@Transactional
	public SharedModel create(SharedModel sharedModel) {
		SharedModel createdShared = sharedModel;
		return sharedRepository.save(createdShared);
	}
	

	/*@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public SharedModel delete(int id) throws ShopNotFound {
		SharedModel deleteShared = sharedRepository.findOne(id);
		
		if (deleteShared == null)
			throw new ShopNotFound();
		
		sharedRepository.delete(deleteShared);
		return deleteShared;
	}*/
	
	@Override
	@Transactional
	public SharedModel findByEmailAndSharedTask(String email, TaskModel shared_task_id) {
		return sharedRepository.findByEmailAndSharedTask(email,shared_task_id);
	}
	
	@Override
	@Transactional
	public List<SharedModel> findAll() {
		return sharedRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public SharedModel update(SharedModel sharedModel) throws ShopNotFound {
		SharedModel updateShared = sharedRepository.findByEmailAndSharedTask(sharedModel.getEmail(),sharedModel.getShared_task_id());
		
		if (updateShared == null)
			throw new ShopNotFound();
		
		updateShared.setEmail(sharedModel.getEmail());
		updateShared.setShared_task_id(sharedModel.getShared_task_id());
		updateShared.setShared_list_id(sharedModel.getShared_list_id());
		updateShared.setList_id(sharedModel.getList_id());
		updateShared.setTask_id(sharedModel.getTask_id());
		updateShared.setSync(sharedModel.getSync());
		
		return updateShared;
	}


	@Override
	public List<SharedModel> findByListId(String list_id) {
		return sharedRepository.findByListId(list_id);
	}


	@Override
	public SharedModel findByTaskId(String findBySharedListId) {
		return sharedRepository.findByTaskId(findBySharedListId);
	}


	@Override
	public List<SharedModel> findBySharedListId(ListModel shared_list_id) {
		return sharedRepository.findBySharedListId(shared_list_id);
	}


	@Override
	public List<SharedModel> getSync(String email) {
		return sharedRepository.getSync(email);
	}


	@Override
	public List<SharedModel> updateSharedSync(ListModel shared_list_id, String email) {
		return sharedRepository.updateSharedSync(shared_list_id, email);
	}

}
