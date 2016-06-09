package com.centauro.service;

import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ArticleModel;
import com.centauro.model.MenuModel;
import com.centauro.model.PageModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;
import com.centauro.views.PageView;


public interface PageService {
	
	public PageModel create(PageModel page);
	public PageModel delete(int id) throws ShopNotFound;
	public List<PageModel> findAll();
	public PageModel update(PageModel page) throws ShopNotFound;
	public PageModel findById(int id);
	public List<PageModel> getShowPage(String title, String alias);

}
