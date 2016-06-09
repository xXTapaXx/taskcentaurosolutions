package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ArticleModel;
import com.centauro.model.MenuModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;
import com.centauro.views.MenuView;


public interface MenuService {
	
	public MenuModel create(MenuModel menu);
	public MenuModel delete(int id) throws ShopNotFound;
	public List<MenuModel> findAll();
	public MenuModel update(MenuModel menu) throws ShopNotFound;
	public MenuModel findById(int id);
	public List<MenuView> findAllSon(int id);
	public List<MenuModel> findAllFather();
}
