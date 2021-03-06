package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.CalendarModel;
import com.centauro.model.TaskModel;
import com.centauro.model.UserModel;
import com.centauro.repository.TaskRepository;
import com.centauro.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserModel create(UserModel userModel) {
		UserModel createdUser = userModel;
		return userRepository.save(createdUser);
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
		UserModel updateUser = userRepository.findOne(user.getId());
		
		if (updateUser == null)
			throw new ShopNotFound();
		
		updateUser.setToken(user.getToken());
		
		return updateUser;
	}

	@Override
	public UserModel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel existUserByEmailAndToken(String email, String token) {
		// TODO Auto-generated method stub
		return userRepository.existUserByEmailAndToken(email, token);
	}

	@Override
	public List<UserModel> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}


}
