package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;


public interface PruebaService {
	
	public PruebaModel create(PruebaModel prueba);
	public PruebaModel delete(int id) throws ShopNotFound;
	public List<PruebaModel> findAll();
	public PruebaModel update(PruebaModel prueba) throws ShopNotFound;
	public PruebaModel findById(int id);
	
}
