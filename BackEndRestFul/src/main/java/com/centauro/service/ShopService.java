package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ShopModel;


public interface ShopService {
	
	public ShopModel create(ShopModel shop);
	public ShopModel delete(int id) throws ShopNotFound;
	public List<ShopModel> findAll();
	public ShopModel update(ShopModel shop) throws ShopNotFound;
	public ShopModel findById(int id);

}
