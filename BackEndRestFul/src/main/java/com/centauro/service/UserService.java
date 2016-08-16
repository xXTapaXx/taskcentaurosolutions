package com.centauro.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.UserModel;


public interface UserService {
	
	public UserModel create(UserModel user);
	public UserModel delete(int id) throws ShopNotFound;
	public List<UserModel> findAll();
	public UserModel update(UserModel user) throws ShopNotFound;
	public UserModel findById(int id);
	public UserModel existUserByEmailAndToken(String email,String token);
	public List<UserModel> findByEmail(String email);
	
}
