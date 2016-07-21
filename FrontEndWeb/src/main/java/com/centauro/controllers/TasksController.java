package com.centauro.controllers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.centauro.view.TaskListView;
import com.centauro.view.TaskView;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;


@Controller
public class TasksController {
	
	 /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

	

	
	@RequestMapping("/taskslists")
    public String allTasksLists(Model model) {
		List<?> tasksLists = null;
		List<Object> listLeft = new ArrayList<Object>();
		List<Object> listCenter = new ArrayList<Object>();
		List<Object> listRight = new ArrayList<Object>();
		ArrayList arraylistResult;
		TaskListView taskView;
		int count = 1;
		try{
		//RestTemplate restTemplate = new RestTemplate();
		//tasksLists = restTemplate.getForObject("http://localhost:8081/taskslists", List.class);
		
    	TaskLists result = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			
			 result = service.tasklists().list().execute();
			 
			 
			 List<TaskList> tasklists = result.getItems();
			 for (TaskList tasklist : tasklists) {
				 
				 com.google.api.services.tasks.model.Tasks tasks = service.tasks().list(tasklist.getId()).execute();
				 arraylistResult = new ArrayList<Task>();
				 taskView = new TaskListView();
				 taskView.setId(tasklist.getId());
				 taskView.setTitle(tasklist.getTitle());
				if(tasks.getItems() != null)
        	   		{
					
					 
				 for (Task task : tasks.getItems()) {
					
					 arraylistResult.add(task);
			            }
		            }
				 
				taskView.setTasks(arraylistResult);
				 	
					if(count == 1){
						listLeft.add(taskView);
						count++;
					}else if(count == 2){
						listCenter.add(taskView);
						count++;
					}else{
						listRight.add(taskView);
						count = 1;
					}
			 	}
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			String[] tasks = request.getParameterValues("task");
			String id = request.getParameter("id");
			TaskList ListTasks = null;
			Task resultTask = null;
			Tasks service = TasksQuickstart.getTasksService();
			
			if(id != null){
				ListTasks = service.tasklists().get(id).execute();
				ListTasks.setTitle(title);

				service.tasklists().update(id, ListTasks).execute();
			}else{
				TaskList taskList = new TaskList();
				taskList.setTitle(title);		
				ListTasks = service.tasklists().insert(taskList).execute();		
			}	 
					 for (String task : tasks) {

				 			Task insertTask = new Task();
						 	insertTask.setTitle(task);
	
						 	resultTask = service.tasks().insert(ListTasks.getId(), insertTask).execute();		 	
			            }   
					 				  
		}
		catch (Exception e) 
		{
			 System.out.println("Error: " + e);  
		} 

		return "taskslists";
	}
	
	@RequestMapping(value = "/getTaskList/{id}")
	public @ResponseBody List<?> getTaskLists(@PathVariable(value="id") String id) {
    	
    	List<TaskListView> response = new ArrayList<TaskListView>();
    	List<Task> arraylistResult = null ;
	    TaskListView taskView = null;
	    TaskList tasksList = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
					
			tasksList = service.tasklists().get(id).execute();
		
				 com.google.api.services.tasks.model.Tasks tasks = service.tasks().list(tasksList.getId()).execute();
				 arraylistResult = new ArrayList<Task>();
				 taskView = new TaskListView();
				 taskView.setId(tasksList.getId());
				 taskView.setTitle(tasksList.getTitle());
				if(tasks.getItems() != null)
        	   		{
					
					 
				 for (Task task : tasks.getItems()) {
					
					 arraylistResult.add(task);
			            }
		            }
				 
				 taskView.setTasks(arraylistResult);
				 response.add(taskView);
			 	
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return response;   
    }
	
	@RequestMapping(value = "/updateTaskStatus", method = RequestMethod.POST)
    public Task updateTaskStatus(HttpServletRequest request) {
    	
    	Task result = null;
    	Task insertTask = null;
    	String status = request.getParameter("status");
    	String id = request.getParameter("id");
    	String listId = request.getParameter("listId");
    	try {
			Tasks service = TasksQuickstart.getTasksService();
 			Task task = service.tasks().get(listId,id).execute();
			
			if(status.equals("needsAction")){
				task.setStatus("completed");
				result = service.tasks().patch(listId, task.getId(), task).execute();
			}else{
				String title = task.getTitle();
				service.tasks().delete(listId,id).execute();
				Task taskList = new Task();
				taskList.setTitle(title);

				insertTask = service.tasks().insert(listId,taskList).execute();

				result = service.tasks().patch(listId, task.getId(), insertTask).execute();
			}
			
			//task.setTitle("Update Lista Prueba APi Google Tasks");
			
			 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    
    @RequestMapping(value = "/updateTask", method = RequestMethod.POST)
    public Task updateTask(HttpServletRequest request) {
    	
    	Task result = null;
    	String title = request.getParameter("title");
    	String id = request.getParameter("id");
    	String listId = request.getParameter("listId");
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			Task task = service.tasks().get(listId,id).execute();
			task.setTitle(title);
			
			 result = service.tasks().update(listId, task.getId(), task).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
	
	

	
}
