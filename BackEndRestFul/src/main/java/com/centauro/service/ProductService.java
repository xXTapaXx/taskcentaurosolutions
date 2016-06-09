package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ArticleModel;
import com.centauro.model.FromLocationModel;
import com.centauro.model.LocationModel;
import com.centauro.model.ProductModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ServiceModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;


public interface ProductService {
	
	public ProductModel create(ProductModel product);
	public ProductModel delete(int id) throws ShopNotFound;
	public List<ProductModel> findAll();
	public ProductModel update(ProductModel product) throws ShopNotFound;
	public ProductModel findById(int id);
	
}
