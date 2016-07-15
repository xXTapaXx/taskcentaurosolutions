package com.centauro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Integer> {
	
	 /* @Query("select u from users u where u.username = :username")
	  public User findByUsername(String username);*/
	 
	@Query("SELECT u FROM UserModel u WHERE u.token = :token")
	    public UserModel existUserByToken(@Param("token") String token);
	
	@Query("SELECT u FROM UserModel u WHERE u.email = :email")
    public List<UserModel> findByEmail(@Param("email") String email);
	  
}
