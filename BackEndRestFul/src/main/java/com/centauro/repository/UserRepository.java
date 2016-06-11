package com.centauro.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Integer> {
	
	 /* @Query("select u from users u where u.username = :username")
	  public User findByUsername(String username);*/
	 
	@Query("SELECT u FROM UserModel u WHERE u.username = :username")
	    public UserModel findByUsername(@Param("username") String username);
	  
}