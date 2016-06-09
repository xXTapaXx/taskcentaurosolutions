package com.centauro.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.MenuModel;
import com.centauro.repository.MenuRepository;
import com.centauro.views.MenuView;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Resource
	private MenuRepository menuRepository;

	@Override
	@Transactional
	public MenuModel create(MenuModel menu) {
		MenuModel createdMenu = menu;
		return menuRepository.save(createdMenu);
	}
	
	
	@Override
	@Transactional
	public List<MenuView> findAllSon(int id){
		List<MenuModel> listMenu = menuRepository.findAllSon(id);
		List<MenuView> menuView = new ArrayList();
		for(MenuModel menus : listMenu){
		menuView.add(new MenuView(menus.getId(),menus.getTitle(),menus.getAlias(),menus.getFather(),menus.getSon()));		
			System.out.println(menuView);
		}
		return menuView ;
	}
	
	@Override
	@Transactional
	public List<MenuModel> findAllFather(){
		return menuRepository.findAllFather();
	}
	

	@Override
	@Transactional
	public MenuModel findById(int id) {
		return menuRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public MenuModel delete(int id) throws ShopNotFound {
		MenuModel deleteMenu = menuRepository.findOne(id);
		
		if (deleteMenu == null)
			throw new ShopNotFound();
		
		menuRepository.delete(deleteMenu);
		return deleteMenu;
	}
	
	

	@Override
	@Transactional
	public List<MenuModel> findAll() {
		return menuRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public MenuModel update(MenuModel menu) throws ShopNotFound {
		MenuModel updateMenu = menuRepository.findOne(menu.getId());
		
		if (updateMenu == null)
			throw new ShopNotFound();
		
		updateMenu.setTitle(menu.getTitle());
		updateMenu.setAlias(menu.getAlias());
		updateMenu.setFather(menu.getFather());
		
		return updateMenu;
	}

}
