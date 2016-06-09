package com.centauro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.PageModel;


public interface PageRepository extends JpaRepository<PageModel, Integer> {
	
	@Query("SELECT p FROM PageModel p WHERE p.title = :title AND  p.alias = :alias")
	public List<PageModel> getShowPage(@Param("title") String title,@Param("alias") String alias);
	
	/*@Query("SELECT m FROM Menu m where padre < 0")
	public List<Menu> findAllFather();*/
}
