package com.centauro.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.exception.ShopNotFound;
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
    public @ResponseBody String registerUser(HttpServletRequest request) {
    	String email = request.getParameter("email");
    	String token = request.getParameter("token");
    	UserModel userModel = userService.existUserByEmailAndToken(email,token);
    	List<?> response = new ArrayList<>();
    	if(userModel == null){
    		UserModel user = new UserModel();
    		user.setEmail(email);
    		user.setToken(token);
    		
    		userService.create(user);
    	}
    	
    	return "Usuario Registrado";
    }
    
	@RequestMapping("/signOut")
    public  List<?> signOut(HttpServletRequest request) {
		//boolean response = false;
		List response = new ArrayList();
		String email = request.getParameter("email");
    	String token = request.getParameter("token");
    	UserModel userModel = userService.existUserByEmailAndToken(email,token);
    	response.add("signOut");
    	if(userModel != null){
    		userModel.setEmail(email);
    		userModel.setToken("Eliminado");
    		
    		try {
				userService.update(userModel);
			} catch (ShopNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		 return response;
		
	}   
}
