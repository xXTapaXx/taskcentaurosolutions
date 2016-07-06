package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ListModel;


public interface ListService {
	
	public ListModel create(ListModel list);
	public ListModel delete(int id) throws ShopNotFound;
	public List<ListModel> findAll();
	public ListModel update(ListModel list) throws ShopNotFound;
	public ListModel findById(int id);
}
