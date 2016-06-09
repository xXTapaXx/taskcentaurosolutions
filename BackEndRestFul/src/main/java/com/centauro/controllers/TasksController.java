package com.centauro.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.view.TaskView;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;

@RestController
public class TasksController {

    TasksQuickstart tasksQuickstart = new TasksQuickstart();
    
    @RequestMapping("/taskslists")
    public List<?> getTasksLists(HttpServletRequest request) {
    	new HashMap<String, String>();
    	List<TaskView> response = new ArrayList<TaskView>();
    	List<Task> arraylistResult = null ;
	    TaskView taskView = null;
    	TaskLists result = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			
			 result = service.tasklists().list().execute();
			 
			 List<TaskList> tasklists = result.getItems();
			 for (TaskList tasklist : tasklists) {
				
				 com.google.api.services.tasks.model.Tasks tasks = service.tasks().list(tasklist.getId()).execute();
				 arraylistResult = new ArrayList<Task>();
				 taskView = new TaskView();
				 taskView.setId(tasklist.getId());
				 taskView.setTitle(tasklist.getTitle());
				if(tasks.getItems() != null)
        	   		{
					
					 
				 for (Task task : tasks.getItems()) {
					
					 arraylistResult.add(task);
			            }
		            }
				 
				 taskView.setTasks(arraylistResult);
				 response.add(taskView);
			 	}
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return response;
    	
        
    }
    
    @RequestMapping("/getTasksList")
    public TaskList getTasksList(HttpServletRequest request) {
    	
    	TaskList result = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			
			 result = service.tasklists().get("MDExNTYzNzMwMjY2MTkyMDg5NTc6MjM0MjU1MTQyOjA").execute();
			 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    
    @RequestMapping("/insertTasksList/{title}")
    public String insertTasksList(@PathVariable(value="title") String title) {
    	
    	TaskList result = null;
    	
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			TaskList taskList = new TaskList();
			taskList.setTitle(title);

			 result = service.tasklists().insert(taskList).execute();
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result.getId();
        
    }
    
    @RequestMapping("/getTaskList/{id}")
    public List<?> getTaskLists(@PathVariable(value="id") String id) {
    	
    	List<TaskView> response = new ArrayList<TaskView>();
    	List<Task> arraylistResult = null ;
	    TaskView taskView = null;
	    TaskList tasksList = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
					
			tasksList = service.tasklists().get(id).execute();
		
				 com.google.api.services.tasks.model.Tasks tasks = service.tasks().list(tasksList.getId()).execute();
				 arraylistResult = new ArrayList<Task>();
				 taskView = new TaskView();
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
    
    @RequestMapping("/updateTasksList")
    public TaskList updateTasksList(HttpServletRequest request) {
    	
    	TaskList result = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			TaskList taskList = service.tasklists().get("MDExNTYzNzMwMjY2MTkyMDg5NTc6MjM0MjU1MTQyOjA").execute();
			taskList.setTitle("Actualizar Prueba Lista");

			 result = service.tasklists().update(taskList.getId(), taskList).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    
    @RequestMapping("/deleteTasksList")
    public boolean deleteTasksList(HttpServletRequest request) {
    	
    	//TaskList result = true;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			
			 service.tasklists().delete("MDExNTYzNzMwMjY2MTkyMDg5NTc6MjM0MjU1MTQyOjA").execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return true;
        
    }
    

    
   
}