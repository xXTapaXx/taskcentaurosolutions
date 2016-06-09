package com.centauro.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.ShopModel;
import com.centauro.model.TextArticleModel;
import com.centauro.model.UserModel;


public interface TextArticleRepository extends JpaRepository<TextArticleModel, Integer> {
	
	
	  
}
