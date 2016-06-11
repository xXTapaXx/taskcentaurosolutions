package com.centauro.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


@Controller
public class TasksController {
	
	@RequestMapping("/taskslists")
    public String allTasksLists(Model model) {
		List<?> tasksLists = null;
		List<Object> listLeft = new ArrayList<Object>();
		List<Object> listCenter = new ArrayList<Object>();
		List<Object> listRight = new ArrayList<Object>();
		int count = 1;
		try{
		RestTemplate restTemplate = new RestTemplate();
		tasksLists = restTemplate.getForObject("http://localhost:8081/taskslists", List.class);
		
		for (Object tasksList : tasksLists) {
			
			if(count == 1){
				listLeft.add(tasksList);
				count++;
			}else if(count == 2){
				listCenter.add(tasksList);
				count++;
			}else{
				listRight.add(tasksList);
				count = 1;
			}
			
			
		}
        
		model.addAttribute("listLeft", listLeft);
		model.addAttribute("listCenter", listCenter);
		model.addAttribute("listRight", listRight);
       
		
		}
		catch (Exception e) 
		{
			 System.out.println("Error: " + e);  
		} 
		return "index";
	}
	
	@RequestMapping(value = "/insertTasks", method = RequestMethod.POST)
    public String insertTasks(HttpServletRequest request, Model model) {
		String idTasksList = null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			String title = request.getParameter("title");
			String[] task = request.getParameterValues("task");
			String id = request.getParameter("id");
			
			idTasksList = restTemplate.getForObject("http://localhost:8081/insertTasksList?title={title}&id={id}", String.class,title,id);
			
			for(Integer i = 0; i < task.length; i++){
										
			restTemplate.getForObject("http://localhost:8081/insertTask?title={task}&idTasksList={idTasksList}", String.class,task[i],idTasksList);
					      
			}
       
		
		}
		catch (Exception e) 
		{
			 System.out.println("Error: " + e);  
		} 

		return "index";
	}
	
	@RequestMapping(value = "/getTaskList", method = RequestMethod.POST)
    public List<?> getTasksLists(HttpServletRequest request, Model model) {
		List<?> taskLists = null;

		int count = 1;
		try{
		RestTemplate restTemplate = new RestTemplate();
		String id = request.getParameter("id");
		taskLists = restTemplate.getForObject("http://localhost:8081/getTaskList/{id}", List.class,id);
			
		}
		catch (Exception e) 
		{
			 System.out.println("Error: " + e);  
		} 
		return taskLists;
	}
	
	

	
}
