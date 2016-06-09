package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ArticleModel;
import com.centauro.model.FromLocationModel;
import com.centauro.model.LocationModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ServiceModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;


public interface FromLocationService {
	
	public FromLocationModel create(FromLocationModel fromLocation);
	public FromLocationModel delete(int id) throws ShopNotFound;
	public List<FromLocationModel> findAll();
	public FromLocationModel update(FromLocationModel fromLocation) throws ShopNotFound;
	public FromLocationModel findById(int id);
	//public List<FromLocationModel> findAllFromLocation();
	public List<FromLocationModel> findLocations(LocationModel location);
	
}
