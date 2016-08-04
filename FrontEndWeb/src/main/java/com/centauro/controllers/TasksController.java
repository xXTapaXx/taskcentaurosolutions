package com.centauro.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.mapred.gethistory_jsp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.centauro.view.SharedModelView;
import com.centauro.view.SharedView;
import com.centauro.view.TaskListView;
import com.centauro.view.TaskModelView;
import com.centauro.view.TaskView;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Controller
public class TasksController {
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/taskslists")
    public String allTasksLists(Model model) {
		//List<SharedModelView> tasksLists = null;
		Gson gson = new Gson();
		List<Object> listLeft = new ArrayList<Object>();
		List<Object> listCenter = new ArrayList<Object>();
		List<Object> listRight = new ArrayList<Object>();
		ArrayList arraylistResult;
		List<SharedModelView> sharedModelView;
		TaskListView taskView;
		int count = 1;
		try{
			RestTemplate restTemplate = new RestTemplate();
			String email = "jason@centaurosolutions.com";
			//	String url = "http://localhost:8081/haveShared?email={email}";
			//URI expanded = new UriTemplate(url).expand(email); // this is what RestTemplate uses 
			
			String tasksLists = restTemplate.getForObject("http://localhost:8081/haveSharedFE?email={email}",String.class,email);
			
			Type type = new TypeToken<List<SharedModelView>>(){}.getType();
            sharedModelView = (List<SharedModelView>) gson.fromJson(tasksLists,type);
			if(sharedModelView.size() > 0){
				insertOrUpdateTask(sharedModelView);
			} 
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
	
	public void insertOrUpdateTask(List<SharedModelView> sharedModelView){
		Gson gson = new Gson();
        TaskList listTasks = null;
        Task resultTask = null;
        String listId = null;
        String listName = null;
        List<?> tasksLists = null;
        try {
        	Tasks mService = TasksQuickstart.getTasksService();

            for(SharedModelView shared : sharedModelView){
                if(!shared.getShared_list_id().getList() .isEmpty()) {
                    if (shared.getList_id() != null && !shared.getList_id().isEmpty() && !shared.getList_id().toString().equals("null")) {

                        if(shared.getShared_list_id().getSync() == 0){
                            try {
                                mService.tasklists().delete(shared.getList_id()).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            listTasks = mService.tasklists().get(shared.getList_id()).execute();
                            listTasks.setTitle(shared.getShared_list_id().getList());

                            mService.tasklists().update(shared.getList_id(), listTasks).execute();
                            listId = listTasks.getId();
                            listName = listTasks.getTitle();
                        }

                    } else if(listId != null && listName.equals(shared.getShared_list_id().getList())){

                        listTasks = mService.tasklists().get(listId).execute();
                        listTasks.setTitle(shared.getShared_list_id().getList());

                        mService.tasklists().update(listId, listTasks).execute();

                    }else{
                        TaskList taskList = new TaskList();
                        taskList.setTitle(shared.getShared_list_id().getList());
                        listTasks = mService.tasklists().insert(taskList).execute();
                        listId = listTasks.getId();
                        listName = listTasks.getTitle();
                    }

                        if(shared.getTask_id() != null  && !shared.getTask_id().toString().equals("null") && shared.getShared_task_id().getSync() == 0){

                            try {
                                mService.tasks().delete(shared.getList_id(), shared.getTask_id()).execute();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            if (shared.getTask_id() != null && !shared.getShared_task_id().getTask().isEmpty() && !shared.getTask_id().toString().equals("null") && shared.getShared_task_id().getSync() > 0) {

                                resultTask = mService.tasks().get(shared.getList_id(), shared.getTask_id()).execute();
                                resultTask.setTitle(shared.getShared_task_id().getTask());
                                resultTask.setStatus(shared.getShared_task_id().getStatus());
                                mService.tasks().update(shared.getList_id(), shared.getTask_id(), resultTask).execute();

                            } else if(!shared.getShared_task_id().getTask().isEmpty() && shared.getShared_task_id().getSync() > 0){
                                Task insertTask = new Task();
                                insertTask.setTitle(shared.getShared_task_id().getTask());
                                resultTask = mService.tasks().insert(listTasks.getId(), insertTask).execute();

                                if(shared.getShared_task_id().getStatus().equals("completed")){
                                    resultTask = mService.tasks().get(listTasks.getId(), resultTask.getId()).execute();
                                    resultTask.setStatus(shared.getShared_task_id().getStatus());
                                    mService.tasks().update(listTasks.getId(), resultTask.getId(), resultTask).execute();
                                }
                            }
                        }

                    }
                shared.setList_id(listTasks.getId());
                shared.setTask_id(resultTask.getId());
                }
            
            Type type = new TypeToken<List<SharedModelView>>(){}.getType();
            String shared = gson.toJson(sharedModelView,type);
            
            RestTemplate restTemplate = new RestTemplate();
            
			String url = "http://localhost:8081/updateSync?shared={shared}";
			URI expanded = new UriTemplate(url).expand(shared); // this is what RestTemplate uses 
			
			tasksLists = restTemplate.postForObject(expanded, null,List.class);
            
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

       
    }
	
	@RequestMapping(value = "/insertTasks", method = RequestMethod.POST)
    public String insertTasks(HttpServletRequest request, Model model) {
		String idTasksList = null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			String title = request.getParameter("title");
			String[] tasks = request.getParameterValues("task");
			String id = request.getParameter("id");
			String date = request.getParameter("date");
			TaskList ListTasks = null;
			Task resultTask = null;
			Tasks service = TasksQuickstart.getTasksService();
			List<TaskView> listTaskView = new ArrayList<>();
			SharedController shared = new SharedController();
			if(id != null){
				ListTasks = service.tasklists().get(id).execute();
				ListTasks.setTitle(title);

				ListTasks = service.tasklists().update(id, ListTasks).execute();
			}else{
				TaskList taskList = new TaskList();
				taskList.setTitle(title);		
				ListTasks = service.tasklists().insert(taskList).execute();		
			}	 
					 if(tasks != null){
						 for (String task : tasks) {
							 TaskView taskView = new TaskView();
					 			Task insertTask = new Task();
							 	insertTask.setTitle(task);
		
							 	resultTask = service.tasks().insert(ListTasks.getId(), insertTask).execute();
							 	
							 	taskView.setId(resultTask.getId());
								taskView.setTitle(resultTask.getTitle());
								taskView.setStatus(resultTask.getStatus());
								taskView.setIsNew(false);
								
								listTaskView.add(taskView);
							 	
				            }
					 }
					 									
						shared.updateShared(ListTasks.getId(), ListTasks.getTitle(), listTaskView,date);
					 
					 
					 				  
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
    	List<TaskView> arraylistResult = null ;
	    TaskListView taskListView = null;
	    TaskList tasksList = null;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
					
			tasksList = service.tasklists().get(id).execute();
		
				 com.google.api.services.tasks.model.Tasks tasks = service.tasks().list(tasksList.getId()).execute();
				 arraylistResult = new ArrayList<TaskView>();
				 taskListView = new TaskListView();
				 taskListView.setId(tasksList.getId());
				 taskListView.setTitle(tasksList.getTitle());
				if(tasks.getItems() != null)
        	   		{
					
					 
				 for (Task task : tasks.getItems()) {
					 	TaskView taskView = new TaskView();
						taskView.setId(task.getId());
						taskView.setTitle(task.getTitle());
						taskView.setStatus(task.getStatus());
						taskView.setIsNew(false);
						
					 arraylistResult.add(taskView);
			            }
		            }
				 
				taskListView.setTasks(arraylistResult);
				 response.add(taskListView);
			 	
			 
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
    	SharedController shared = new SharedController();
    	List<TaskView> listTaskView = new ArrayList<TaskView>();
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			Task task = service.tasks().get(listId,id).execute();
			task.setTitle(title);
			
			 result = service.tasks().update(listId, task.getId(), task).execute();
			
				TaskList taskList = service.tasklists().get(listId).execute();
				com.google.api.services.tasks.model.Tasks tasks = service.tasks().list(taskList.getId()).execute();
			
				for( Task taskResult : tasks.getItems()){
					TaskView taskView = new TaskView();
					if(id.equals(taskResult.getId())){
						taskView.setId(id);
						taskView.setTitle(title);
						
					}else{
						taskView.setId(taskResult.getId());
						taskView.setTitle(taskResult.getTitle());
					}
					
					taskView.setStatus(taskResult.getStatus());
					taskView.setIsNew(false);
					
					listTaskView.add(taskView);
				}
				
				shared.updateShared(taskList.getId(), taskList.getTitle(), listTaskView,null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    
    @RequestMapping(value = "/updateTasksList", method = RequestMethod.POST)
    public TaskList updateTasksList(HttpServletRequest request) {
    	
    	TaskList result = null;
    	String id = request.getParameter("id");
    	String title = request.getParameter("title");
    	SharedController shared = new SharedController();
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			TaskList taskList = service.tasklists().get(id).execute();
			taskList.setTitle(title);

			 result = service.tasklists().update(taskList.getId(), taskList).execute();
			 
			 
			 shared.updateShared(id, title, null,null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
        
    }
    
    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public @ResponseBody String deleteList(HttpServletRequest request) {
    	
    	Gson gson = new Gson();
    	Type type = new TypeToken<List<SharedModelView>>(){}.getType();
    	String listId = request.getParameter("listId");
    	List<SharedModelView> sharedModelView;
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			
			RestTemplate restTemplate = new RestTemplate();
			String email = "jason@centaurosolutions.com";
			String tasksLists = restTemplate.getForObject("http://localhost:8081/haveSharedFE?email={email}",String.class,email);
			
			
            sharedModelView = (List<SharedModelView>) gson.fromJson(tasksLists,type);
			if(sharedModelView.size() > 0){
				 for(SharedModelView shared : sharedModelView){

		                if(listId.equals(shared.getList_id())){
		                    shared.setSync(0);
		                    shared.getShared_list_id().setSync(0);
		                    shared.getShared_task_id().setSync(0);
		                }

		            }
				 String url = "http://localhost:8081/updateDeleteList?shared={shared}";
				 String sharedRequest = gson.toJson(sharedModelView,type);
				 URI expanded = new UriTemplate(url).expand(sharedRequest); // this is what RestTemplate uses 
				 
				 List<?> result = restTemplate.postForObject(expanded,null,List.class);
			} 
			
			//service.tasklists().delete(listId).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "Se Eliminó la lista exitosamente";
        
    }
    
    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    public @ResponseBody String deleteTask(HttpServletRequest request) {
    	
    	Gson gson = new Gson();
    	Type type = new TypeToken<List<SharedModelView>>(){}.getType();
    	String taskId = request.getParameter("taskId");
    	String listId = request.getParameter("listId");
    	List<SharedModelView> sharedModelView;
    	SharedView sharedView = new SharedView();;
    	TaskListView taskListView = new TaskListView();
    	TaskView taskView = new TaskView();
    	List<TaskView> listTaskView = new ArrayList<>();
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			
			RestTemplate restTemplate = new RestTemplate();
			String email = "jason@centaurosolutions.com";
			String tasksLists = restTemplate.getForObject("http://localhost:8081/haveSharedFE?email={email}",String.class,email);
			
			
            sharedModelView = (List<SharedModelView>) gson.fromJson(tasksLists,type);
			if(sharedModelView.size() > 0){
				 for(SharedModelView shared : sharedModelView){

		                if(taskId.equals(shared.getTask_id())){
		                	
		                	taskView.setId(shared.task_id);
                			taskView.setIsNew(false);
                			taskView.setTitle(shared.getShared_task_id().getTask());
                			taskView.setStatus(shared.getShared_task_id().getStatus());
                			
                			listTaskView.add(taskView);
                			
		                	taskListView.setId(shared.getList_id());
		                	taskListView.setTitle(shared.getShared_list_id().getList());		                	
		                	taskListView.setDate(null);
		                	taskListView.setTasks(listTaskView);
		                	
		                	sharedView.setMyEmail(email);
		                	sharedView.setEmails(null);
		                	sharedView.setTaskListView(taskListView);
		                	
		                }

		            }
				 String url = "http://localhost:8081/updateDeleteTask?shared={shared}";
				 String sharedRequest = gson.toJson(sharedView,SharedView.class);
				 URI expanded = new UriTemplate(url).expand(sharedRequest); // this is what RestTemplate uses 
				 
				 List<?> result = restTemplate.getForObject(expanded,List.class);
			}
			//service.tasks().delete(listId, taskId).execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "Se Eliminó la tarea exitosamente";
        
    }
	
	

	
}
