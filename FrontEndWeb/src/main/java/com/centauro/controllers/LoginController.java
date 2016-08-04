package com.centauro.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centauro.view.SharedModelView;
import com.centauro.view.TaskListView;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.gson.Gson;


@Controller
public class LoginController {
	
	 /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();
    

	@RequestMapping( "/login")
    public String Login(Model model) {
	
			//TasksQuickstart.getTasksService();
		Gson gson = new Gson();
		List<Object> listLeft = new ArrayList<Object>();
		List<Object> listCenter = new ArrayList<Object>();
		List<Object> listRight = new ArrayList<Object>();
		ArrayList arraylistResult;
		List<SharedModelView> sharedModelView;
		TaskListView taskView;
		int count = 1;
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
	       
			
			 

			return "index";
		
	}
}
