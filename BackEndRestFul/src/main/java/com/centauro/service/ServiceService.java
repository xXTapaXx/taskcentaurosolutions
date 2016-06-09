package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ArticleModel;
import com.centauro.model.LocationModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ServiceModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;


public interface ServiceService {
	
	public ServiceModel create(ServiceModel service);
	public ServiceModel delete(int id) throws ShopNotFound;
	public List<ServiceModel> findAll();
	public ServiceModel update(ServiceModel service) throws ShopNotFound;
	public ServiceModel findById(int id);
	
}
