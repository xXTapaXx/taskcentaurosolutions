package com.centauro.controllers;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.CalendarModel;
import com.centauro.model.ListModel;
import com.centauro.model.SharedModel;
import com.centauro.model.TaskModel;
import com.centauro.model.UserModel;
import com.centauro.service.CalendarService;
import com.centauro.service.ListService;
import com.centauro.service.SharedService;
import com.centauro.service.TaskService;
import com.centauro.service.UserService;
import com.centauro.view.ListModelView;
import com.centauro.view.SharedModelView;
import com.centauro.view.SharedView;
import com.centauro.view.TaskListView;
import com.centauro.view.TaskModelView;
import com.google.api.services.tasks.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class SharedController {
	
	@Autowired
	private SharedService sharedService;
	
	@Autowired
	private ListService listService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/insertShared") 
    public String insertShared(HttpServletRequest request) {
    	
    	Gson gson = new Gson();
    	String shared = request.getParameter("shared");
    	SharedView sharedView = gson.fromJson(shared, SharedView.class);
    	List<SharedModel> sharedModel = sharedService.findByListId(sharedView.getTaskListView().getId());
    	TaskListView taskListView = sharedView.getTaskListView();
    	String response = "Error al compartir la lista de tareas";
    	ListModel listModelResult = null;
    	if(sharedModel.size() > 0){
    		
    		listModelResult = createSharedList(sharedModel.get(0),taskListView);
    		updateOrCreateTask(taskListView,sharedView,listModelResult);
    		response = "Se compartió la lista de Tareas correctamente";
    	} else{
    		createSharedTaskAndList(taskListView,sharedView);
    		response = "Se compartió la lista de Tareas correctamente";
    	}
    	return response;
    }
	
	public ListModel createSharedList(SharedModel sharedModel,TaskListView taskListView){
		ListModel listModelResult = null;
		//Actualizamos la Lista
		ListModel listModel = listService.findById(sharedModel.getShared_list_id().getId());
		
		listModel.setList(taskListView.getTitle());
		
		try {
			listModelResult = listService.update(listModel);
		} catch (ShopNotFound e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return listModelResult;
	}
	
	public void updateOrCreateTask(TaskListView taskListView,SharedView sharedView, ListModel resultIdList){
		TaskModel taskModelResult = null;
		for(Task task : taskListView.getItems()){
			//Actualizamos las tareas
			SharedModel taskSharedModel = sharedService.findByTaskId(task.getId());
			
			if(taskSharedModel != null){
				TaskModel taskModel = taskService.findById(taskSharedModel.getShared_task_id().getId());
        		
        		taskModel.setTask(task.getTitle());
        		taskModel.setStatus(task.getStatus());
        		
        		try {
        			taskModelResult = taskService.update(taskModel);
				} catch (ShopNotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				TaskModel taskModelCreate = new TaskModel();
				
				taskModelCreate.setTask(task.getTitle());
				taskModelCreate.setStatus(task.getStatus());
				taskModelCreate.setTask_list_id(resultIdList.getId());
				
				taskModelResult = taskService.create(taskModelCreate);
				
				List<SharedModel> getListSharedListId = sharedService.findBySharedListId(resultIdList);
				
				for(SharedModel getSharedListId : getListSharedListId){
					
					SharedModel sharedModelCreate = new SharedModel();
					
					sharedModelCreate.setEmail(getSharedListId.getEmail());
					sharedModelCreate.setShared_task_id(taskModelResult);
					sharedModelCreate.setShared_list_id(getSharedListId.getShared_list_id());
					if(getSharedListId.getEmail().equals(sharedView.getMyEmail())){
						sharedModelCreate.setList_id(taskListView.getId());
						sharedModelCreate.setTask_id(task.getId());
						
					}
					
					
					sharedService.create(sharedModelCreate);				
				}					
			}
			
			insertOrUpdateSharedEmail(sharedView,taskModelResult,resultIdList,task,taskListView);		
		}
	}
	
	public void createSharedTaskAndList(TaskListView taskListView,SharedView sharedView){
		ListModel listModel = new ListModel();
		listModel.setList(taskListView.getTitle());
		
		ListModel resultIdList = listService.create(listModel);
		
		for(Task task: taskListView.getItems()){
			TaskModel taskModel = new TaskModel();
			taskModel.setTask(task.getTitle());
			taskModel.setTask_list_id(resultIdList.getId());
			taskModel.setStatus(task.getStatus());
			
			TaskModel resultTaskId = taskService.create(taskModel);
			
			insertOrUpdateSharedEmail(sharedView,resultTaskId,resultIdList,task,taskListView);
		}
	}
	
	public void insertOrUpdateSharedEmail(SharedView sharedView,TaskModel resultTaskId,ListModel resultIdList,Task task,TaskListView taskListView){
		
		if(sharedView.getEmails() != null){
			for(String email : sharedView.getEmails()){
				SharedModel sharedModelCreate = new SharedModel();
				SharedModel sharedModelCreateMyEmail = new SharedModel();
				SharedModel findMyEmail = sharedService.findByEmailAndSharedTask(sharedView.getMyEmail(), resultTaskId);
				SharedModel findEmail = sharedService.findByEmailAndSharedTask(email, resultTaskId);
				
				if(findMyEmail == null){
					//My Email
		    		sharedModelCreateMyEmail.setEmail(sharedView.getMyEmail());
		    		sharedModelCreateMyEmail.setShared_task_id(resultTaskId);
		    		sharedModelCreateMyEmail.setShared_list_id(resultIdList);
		    		sharedModelCreateMyEmail.setList_id(taskListView.getId());
		    		sharedModelCreateMyEmail.setTask_id(task.getId());
		    		sharedService.create(sharedModelCreateMyEmail);
				}
				 
				if(findEmail == null){
					//Email
					sharedModelCreate.setEmail(email);
					sharedModelCreate.setShared_task_id(resultTaskId);
					sharedModelCreate.setShared_list_id(resultIdList);
					sharedService.create(sharedModelCreate);
				}
			}
		}else{
			SharedModel findMyEmail = sharedService.findByEmailAndSharedTask(sharedView.getMyEmail(), resultTaskId);
			SharedModel sharedModelCreateMyEmail = new SharedModel();
			
			if(findMyEmail == null){
				//My Email
	    		sharedModelCreateMyEmail.setEmail(sharedView.getMyEmail());
	    		sharedModelCreateMyEmail.setShared_task_id(resultTaskId);
	    		sharedModelCreateMyEmail.setShared_list_id(resultIdList);
	    		sharedModelCreateMyEmail.setList_id(taskListView.getId());
	    		sharedModelCreateMyEmail.setTask_id(task.getId());
	    		sharedService.create(sharedModelCreateMyEmail);
			}
		}
	}
	
	@RequestMapping("/haveShared") 
    public List<SharedModelView> haveShared(HttpServletRequest request) {
    	
    	Gson gson = new Gson();
    	String email = request.getParameter("email");
   
    	List<SharedModel> sharedModelResult = sharedService.getSync(email);
    	List<SharedModelView> sharedModelViewResponse = new ArrayList<SharedModelView>();
    	for(SharedModel shared : sharedModelResult){
    		
    		TaskModelView taskModelView = new TaskModelView(shared.getShared_task_id().getId(),shared.getShared_task_id().getTask(),shared.getShared_task_id().getTask_list_id(),shared.getShared_task_id().getStatus(),shared.getShared_task_id().getSync());
    		
    		ListModelView listModelView = new ListModelView(shared.getShared_list_id().getId(),shared.getShared_list_id().getList(),shared.getShared_list_id().getSync());
    		
    		SharedModelView sharedModelView = new SharedModelView(shared.getEmail(),taskModelView,listModelView,shared.getList_id(),shared.getTask_id(),shared.getSync());
    		
    		
    		sharedModelViewResponse.add(sharedModelView);
    		
    	}
    	
    	//String response = gson.toJson(sharedModelViewResponse,SharedModelView.class);
    	
    	return sharedModelViewResponse;
    }
	
	//@RequestMapping("/updateSync") 
	@RequestMapping(value = "/updateSync", method = RequestMethod.POST)
    public List<?> updateSync(HttpServletRequest request) {
    	
    	Gson gson = new Gson();
    	String sharedRequest = request.getParameter("shared");
    	List<String> response = new ArrayList<String>(); 
    	Type type = new TypeToken<List<SharedModelView>>(){}.getType();
    	List<SharedModelView> sharedModelViewResponse = gson.fromJson(sharedRequest,type);
    	
    	for(SharedModelView shared : sharedModelViewResponse){
    		
    		TaskModel taskModel = taskService.findById(shared.getShared_task_id().getId());
    		SharedModel updateShared = sharedService.findByEmailAndSharedTask(shared.getEmail(), taskModel);
    		updateShared.setList_id(shared.getList_id());
    		updateShared.setTask_id(shared.getTask_id());
    		
    		try {
				sharedService.update(updateShared);
			} catch (ShopNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return response;
    }
	
	//@RequestMapping("/updateSync") 
		@RequestMapping(value = "/updateDeleteList", method = RequestMethod.POST)
	    public List<?> updateDeleteList(HttpServletRequest request) {
	    	
	    	Gson gson = new Gson();
	    	String sharedRequest = request.getParameter("shared");
	    	List<String> response = new ArrayList<String>(); 
	    	Type type = new TypeToken<List<SharedModelView>>(){}.getType();
	    	List<SharedModelView> sharedModelViewResponse = gson.fromJson(sharedRequest,type);
	    	
	    	for(SharedModelView shared : sharedModelViewResponse){
	    		
	    		TaskModel taskModel = taskService.findById(shared.getShared_task_id().getId());
	    		SharedModel updateShared = sharedService.findByEmailAndSharedTask(shared.getEmail(), taskModel);
	    		
	    		updateShared.setSync(shared.getSync());
	    		updateShared.getShared_list_id().setSync(shared.getShared_list_id().getSync());
	    		updateShared.getShared_task_id().setSync(shared.getShared_task_id().getSync());
	    		
	    		try {
					sharedService.update(updateShared);
				} catch (ShopNotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	return response;
	    }
		
		@RequestMapping("/updateDeleteTask") 
	    public List<SharedModel> updateDeleteTask(HttpServletRequest request) {
	    	
			Gson gson = new Gson();
	    	String shared = request.getParameter("shared");
	    	SharedView sharedView = gson.fromJson(shared, SharedView.class);
	    	SharedModel sharedModel = sharedService.findByTaskId(sharedView.getTaskListView().getItems().get(0).getId());
		    TaskListView taskListView = sharedView.getTaskListView();
	    	List<SharedModel> response = new ArrayList<SharedModel>();
	    	ListModel listModelResult = null;
	    	if(sharedModel != null){
	    		
	    		sharedModel.getShared_task_id().setSync(0);
		    		try {
						sharedService.update(sharedModel);
					} catch (ShopNotFound e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	
	    	} 
	    	
	    	return response;
	    }
		
		
	
	@RequestMapping("/updateShared") 
    public List<SharedModel> updateShared(HttpServletRequest request) {
    	
		Gson gson = new Gson();
    	String shared = request.getParameter("shared");
    	SharedView sharedView = gson.fromJson(shared, SharedView.class);
    	List<SharedModel> sharedModel = sharedService.findByListId(sharedView.getTaskListView().getId());
    	TaskListView taskListView = sharedView.getTaskListView();
    	List<SharedModel> response = new ArrayList<SharedModel>();
    	ListModel listModelResult = null;
    	if(sharedModel.size() > 0){
    		listModelResult = createSharedList(sharedModel.get(0),taskListView);
    		updateOrCreateTask(taskListView,sharedView,listModelResult);
    	} 
    	
    	if(sharedView.getTaskListView().getDate() != null && !sharedView.getTaskListView().getDate().isEmpty()){
    		
    		CalendarModel existCalendar = calendarService.existCalendarByList(sharedView.getTaskListView().getId());
    		
		    if(existCalendar == null){
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			    Date parsedDate = null;
				try {
					parsedDate = dateFormat.parse(sharedView.getTaskListView().getDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	    		
			    List<UserModel> user = userService.findByEmail(sharedView.getMyEmail());	
			    
	    		CalendarModel calendarModel = new CalendarModel();
	    		calendarModel.setUser_id(user.get(0));
	    		calendarModel.setList(sharedView.getTaskListView().getId());
	    		calendarModel.setDate(timestamp);
	    		
	    		calendarService.create(calendarModel);
	    		
	    			
	    		
		    }
    		
    	}
    	
    	return response;
    }
}
