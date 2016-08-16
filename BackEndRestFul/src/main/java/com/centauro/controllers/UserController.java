package com.centauro.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.model.UserModel;
import com.centauro.service.UserService;
import com.centauro.view.TaskListView;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskLists;
import com.google.gson.Gson;

@RestController
public class UserController {

    @Autowired
	private UserService userService;
    
   // @RequestMapping("/registerUser")
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public void registerUser(HttpServletRequest request) {
    	String email = request.getParameter("email");
    	String token = request.getParameter("token");
    	UserModel userModel = userService.existUserByEmailAndToken(email,token);
    	
    	if(userModel == null){
    		UserModel user = new UserModel();
    		user.setEmail(email);
    		user.setToken(token);
    		
    		userService.create(user);
    	}
    }
    

    
   
}
