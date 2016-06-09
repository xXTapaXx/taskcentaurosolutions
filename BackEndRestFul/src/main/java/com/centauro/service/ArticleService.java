package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ArticleModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;


public interface ArticleService {
	
	public ArticleModel create(ArticleModel articleModel);
	public ArticleModel delete(int id) throws ShopNotFound;
	public List<ArticleModel> findAll();
	public ArticleModel update(ArticleModel articleModel) throws ShopNotFound;
	public ArticleModel findById(int id);
	
}
