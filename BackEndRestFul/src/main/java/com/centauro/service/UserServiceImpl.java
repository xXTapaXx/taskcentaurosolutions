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
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;
import com.centauro.model.UserModel;
import com.centauro.repository.PruebaRepository;
import com.centauro.repository.ShopRepository;
import com.centauro.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserModel create(UserModel user) {
		UserModel createdUser = user;
		return userRepository.save(createdUser);
	}
	
	

	@Override
	public UserModel validateUser(String username) {
		return userRepository.findByUsername(username);
	
	}
	
	@Override
	@Transactional
	public UserModel findById(int id) {
		return userRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public UserModel delete(int id) throws ShopNotFound {
		UserModel deletedUser = userRepository.findOne(id);
		
		if (deletedUser == null)
			throw new ShopNotFound();
		
		userRepository.delete(deletedUser);
		return deletedUser;
	}
	
	

	@Override
	@Transactional
	public List<UserModel> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public UserModel update(UserModel user) throws ShopNotFound {
		UserModel updatedUser = userRepository.findOne(user.getId());
		
		if (updatedUser == null)
			throw new ShopNotFound();
		
		updatedUser.setUsername(user.getUsername());
		updatedUser.setPassword(user.getPassword());
		return updatedUser;
	}

}
