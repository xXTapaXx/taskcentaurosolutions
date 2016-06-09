package com.centauro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.MenuModel;


public interface MenuRepository extends JpaRepository<MenuModel, Integer> {
	
	@Query("SELECT m FROM MenuModel m where padre = :id")
	public List<MenuModel> findAllSon(@Param("id") Integer id);
	
	@Query("SELECT m FROM MenuModel m where padre < 0")
	public List<MenuModel> findAllFather();
}
