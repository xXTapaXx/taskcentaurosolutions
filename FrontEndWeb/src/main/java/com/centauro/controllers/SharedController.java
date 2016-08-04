package com.centauro.controllers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import org.springframework.web.util.UriTemplate;

import com.centauro.view.SharedView;
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
import com.google.gson.Gson;



@Controller
public class SharedController {
	
	 /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

	

 
    @RequestMapping(value = "/sharedList", method = RequestMethod.POST)
    public Task updateTask(HttpServletRequest request) {
    	Gson gson = new Gson();
    	Task result = null;
    	String[] emails = request.getParameterValues("email");
    	String id = request.getParameter("id");
    	SharedView sharedView = new SharedView();
    	TaskListView taskListView = new TaskListView();
    	List<String> emailList = new ArrayList<String>();
    	List<TaskView> listTaskView = new ArrayList<TaskView>();
    	try {
			Tasks service = TasksQuickstart.getTasksService();
			TaskList taskList = service.tasklists().get(id).execute();
			com.google.api.services.tasks.model.Tasks task = service.tasks().list(taskList.getId()).execute();
			taskListView.setId(taskList.getId());
			taskListView.setTitle(taskList.getTitle());
			
			for( Task taskResult : task.getItems()){
				TaskView taskView = new TaskView();
				taskView.setId(taskResult.getId());
				taskView.setTitle(taskResult.getTitle());
				taskView.setStatus(taskResult.getStatus());
				taskView.setIsNew(false);
				
				listTaskView.add(taskView);
			}
			taskListView.setTasks(listTaskView);
			taskListView.setDate("");
			sharedView.setMyEmail("jason@centaurosolutions.com");
			
			for (String email : emails) {					
				emailList.add(email);				 	
            } 
			
			sharedView.setEmails(emailList);
			sharedView.setTaskListView(taskListView);
			
			String shared = gson.toJson(sharedView, SharedView.class);
			
			RestTemplate restTemplate = new RestTemplate();
			
			String url = "http://localhost:8081/insertShared?shared={shared}";
			URI expanded = new UriTemplate(url).expand(shared); // this is what RestTemplate uses 
			url = URLDecoder.decode(expanded.toString(), "UTF-8"); // java.net class
			
	        //String sharedResult = restTemplate.getForObject("http://tasks-dev.us-west-2.elasticbeanstalk.com/insertShared?shared={shared}", String.class,shared);
			String sharedResult = restTemplate.getForObject(expanded, String.class);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
         //Convertimos la respuesta json a una lista de TaskListView
        // String shared = gson.toJson(sharedView, SharedView.class);
             	
    	return result;
        
    }
    
    public Task updateShared(String idList, String titleList,List<TaskView> listTaskView, String date) {
    	Gson gson = new Gson();
    	Task result = null;

    	SharedView sharedView = new SharedView();
    	TaskListView taskListView = new TaskListView();
    	List<String> emailList = new ArrayList<String>();

			taskListView.setId(idList);
			taskListView.setTitle(titleList);
			
			taskListView.setTasks(listTaskView);
			taskListView.setDate(date);
			
			sharedView.setMyEmail("jason@centaurosolutions.com");				
			sharedView.setEmails(null);
			sharedView.setTaskListView(taskListView);
			
			String shared = gson.toJson(sharedView, SharedView.class);
			
			RestTemplate restTemplate = new RestTemplate();
			
			String url = "http://localhost:8081/updateShared?shared={shared}";
			URI expanded = new UriTemplate(url).expand(shared); // this is what RestTemplate uses 
			
	        //String sharedResult = restTemplate.getForObject("http://tasks-dev.us-west-2.elasticbeanstalk.com/insertShared?shared={shared}", String.class,shared);
			String sharedResult = restTemplate.getForObject(expanded, String.class);
			           	
    	return result;
        
    }
	
	

	
}
