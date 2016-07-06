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
import com.centauro.model.ListModel;
import com.centauro.repository.ListRepository;


@Service
public class ListServiceImpl implements ListService {
	
	@Resource
	private ListRepository listRepository;

	@Override
	@Transactional
	public ListModel create(ListModel listModel) {
		ListModel createdList = listModel;
		return listRepository.save(createdList);
	}
	

	@Override
	@Transactional
	public ListModel findById(int id) {
		return listRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public ListModel delete(int id) throws ShopNotFound {
		ListModel deleteList = listRepository.findOne(id);
		
		if (deleteList == null)
			throw new ShopNotFound();
		
		listRepository.delete(deleteList);
		return deleteList;
	}
	
	

	@Override
	@Transactional
	public List<ListModel> findAll() {
		return listRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public ListModel update(ListModel listModel) throws ShopNotFound {
		ListModel updateList = listRepository.findOne(listModel.getId());
		
		if (updateList == null)
			throw new ShopNotFound();
		
		updateList.setList(updateList.getList());
		
		return updateList;
	}

	

}
