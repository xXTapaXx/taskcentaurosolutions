package com.centauro.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ListModel;
import com.centauro.model.SharedModel;
import com.centauro.model.TaskModel;
import com.centauro.service.ListService;
import com.centauro.service.SharedService;
import com.centauro.service.TaskService;
import com.centauro.view.SharedView;
import com.centauro.view.TaskListView;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.gson.Gson;

import scala.annotation.meta.getter;

@RestController
public class TasksController {
	
	

    TasksQuickstart tasksQuickstart = new TasksQuickstart();
    
    @RequestMapping("/taskslists2") 
    public List<?> getTasksLists2(HttpServletRequest request) {
    	Gson gson = new Gson();
    	TaskList ListTasks = null;
    	Task resultTask = null;
    	String shared = request.getParameter("shared");
    	SharedView taskView = gson.fromJson(shared, SharedView.class);
			
			
    		
    	return null;
    	
        
    }
    
    @RequestMapping("/taskslists") 
    public TaskLists getTasksLists(HttpServletRequest request) {
    	new HashMap<String, String>();
    	List<TaskListView> response = new LinkedList<TaskListView>();
    	List<Task> arraylistResult = null ;
	    TaskListView taskView = null;
    	TaskLists result = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			
			 result = service.tasklists().list().execute();
			 
			 
			 List<TaskList> tasklists = result.getItems();
			 for (TaskList tasklist : tasklists) {
				 
				/* if(!tasklist.getTitle().equals("Lista de Jason Gamboa")){
					 service.tasklists().delete(tasklist.getId()).execute();
				 }*/
				 
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
				 response.add(taskView);
			 	}
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    	
        
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
    
    @RequestMapping("/insertTasksList")
    public String insertTasksList(HttpServletRequest request) {
    	Gson gson = new Gson();
    	TaskList ListTasks = null;
    	Task resultTask = null;
    	//String title = request.getParameter("title");
    	//String id = request.getParameter("id");
    	String tasks = request.getParameter("tasks");
    	TaskListView taskView = gson.fromJson(tasks, TaskListView.class);
    	try {
    		
			Tasks service = TasksQuickstart.getTasksService();
			
			if(taskView.getId() != null){
				ListTasks = service.tasklists().get(taskView.getId()).execute();
				ListTasks.setTitle(taskView.getTitle());

				service.tasklists().update(taskView.getId(), ListTasks).execute();
			}else{
				TaskList taskList = new TaskList();
				taskList.setTitle(taskView.getTitle());		
				ListTasks = service.tasklists().insert(taskList).execute();		
			}	 
					 for (Task task : taskView.getItems()) {
						
						 	if(task.getId() != null){
						 		
						 		
						 		resultTask = service.tasks().get(taskView.getId(),task.getId()).execute();
						 		resultTask.setTitle(task.getTitle());
								resultTask.setStatus(task.getStatus());
								 service.tasks().update(taskView.getId(), task.getId(), task).execute();
								 
						 		}else{
						 			Task insertTask = new Task();
								 	insertTask.setTitle(task.getTitle());
			
								 	resultTask = service.tasks().insert(ListTasks.getId(), insertTask).execute();
						 		}
						 	
				            }
		            

							 
			
					 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return ListTasks.getId();
        
    }
    
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
    
    @RequestMapping("/getTaskList/{id}")
    public List<?> getTaskLists(@PathVariable(value="id") String id) {
    	
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
    
    @RequestMapping(value = "/updateTasksList", method = RequestMethod.POST)
    public TaskList updateTasksList(HttpServletRequest request) {
    	
    	TaskList result = null;
    	String id = request.getParameter("id");
    	String title = request.getParameter("title");
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			TaskList taskList = service.tasklists().get(id).execute();
			taskList.setTitle(title);

			 result = service.tasklists().update(taskList.getId(), taskList).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    

    
    
    @RequestMapping(value = "/updateTaskStatus", method = RequestMethod.POST)
    public Task updateTaskStatus(HttpServletRequest request) {
    	
    	Task result = null;
    	Task insertTask = null;
    	String status = request.getParameter("status");
    	String id = request.getParameter("id");
    	String listId = request.getParameter("listId");
    	try {
			Tasks service = tasksQuickstart.getTasksService();
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
			Tasks service = tasksQuickstart.getTasksService();
			Task task = service.tasks().get(listId,id).execute();
			task.setTitle(title);
			
			 result = service.tasks().update(listId, task.getId(), task).execute();
			
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