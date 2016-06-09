package com.centauro.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.ArticleModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;


public interface ArticleRepository extends JpaRepository<ArticleModel, Integer> {
	
	
}
