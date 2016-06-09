package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;
import com.centauro.model.TextArticleModel;
import com.centauro.model.UserModel;


public interface TextArticleService {
	
	public TextArticleModel create(TextArticleModel text_article);
	public TextArticleModel delete(int id) throws ShopNotFound;
	public List<TextArticleModel> findAll();
	public TextArticleModel update(TextArticleModel text_article) throws ShopNotFound;
	public TextArticleModel findById(int id);
	
}
