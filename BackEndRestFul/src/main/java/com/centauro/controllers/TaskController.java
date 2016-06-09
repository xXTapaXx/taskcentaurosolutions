package com.centauro.controllers;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.service.PruebaService;
import com.centauro.views.TaskView;
import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;

@RestController
public class TaskController {

    private static final String template = "Welcome, %s!";
    private final AtomicLong counter = new AtomicLong();
    TasksQuickstart tasksQuickstart = new TasksQuickstart();
    
    @Autowired
	private PruebaService pruebaService;
    
    
    
    /*@RequestMapping("/getTaskList")
    public Task getTaskList(HttpServletRequest request) {
    	
    	Task result = null;
    	try {
			Tasks service = tasksQuickstart.getTasksService();
			
			 result = service.tasks().get("@default", "MDExNTYzNzMwMjY2MTkyMDg5NTc6MDo2MDU5NDAzNjM").execute();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }*/
    
    @RequestMapping("/insertTask")
    public Task insertTaskList(HttpServletRequest request) {
    	
    	Task result = null;
    	try {
			Tasks service = tasksQuickstart.getTasksService();
			Task task = new Task();
			task.setTitle(request.getParameter("title"));

			 result = service.tasks().insert(request.getParameter("idTasksList"), task).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    
    @RequestMapping("/updateTaskList")
    public Task updateTaskList(HttpServletRequest request) {
    	
    	Task result = null;
    	try {
			Tasks service = tasksQuickstart.getTasksService();
			Task task = service.tasks().get("@default", "MDExNTYzNzMwMjY2MTkyMDg5NTc6MDo2MzQ3MjgyNjA").execute();
			task.setStatus("completed");
			//task.setTitle("Update Lista Prueba APi Google Tasks");
			
			 result = service.tasks().update("@default", task.getId(), task).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    
    @RequestMapping("/deleteTaskList")
    public boolean deleteTaskList(HttpServletRequest request) {
    	
    	//TaskList result = true;
    	try {
			Tasks service = tasksQuickstart.getTasksService();
			
			service.tasks().delete("@default", "MDExNTYzNzMwMjY2MTkyMDg5NTc6MDo2MDcxOTY2MDY").execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return true;
        
    }
    
    @RequestMapping("/clearTasksList")
    public boolean clearTaskList(HttpServletRequest request) {
    	
    	//TaskList result = true;
    	try {
			Tasks service = tasksQuickstart.getTasksService();
			
			service.tasks().clear("MDExNTYzNzMwMjY2MTkyMDg5NTc6MDo2MzQ3MjgyNjA").execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return true;
        
    }
    
   
    
    
    

    
   
}