package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ArticleModel;
import com.centauro.model.LocationModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;


public interface LocationService {
	
	public LocationModel create(LocationModel article);
	public LocationModel delete(int id) throws ShopNotFound;
	public List<LocationModel> findAll();
	public LocationModel update(LocationModel article) throws ShopNotFound;
	public LocationModel findById(int id);
	
}
