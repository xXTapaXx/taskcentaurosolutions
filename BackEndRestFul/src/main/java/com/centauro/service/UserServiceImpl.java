package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.UserModel;
import com.centauro.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public UserModel create(UserModel user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel delete(int id) throws ShopNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel update(UserModel user) throws ShopNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel existUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
