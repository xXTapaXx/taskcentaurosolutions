package com.centauro.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.CalendarModel;
import com.centauro.model.UserModel;
import com.centauro.service.CalendarService;
import com.centauro.service.UserService;

import scala.collection.mutable.HashMap;



@RestController
public class MessageController {
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/message")
    public  void Message(String token,String listId) throws IOException {
		String url="https://fcm.googleapis.com/fcm/send";
		URL object=new URL(url);

		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		//con.setRequestProperty("Authorization", "key=AIzaSyAnZJjGNCIx6h0wU9H5nYBDtFMa8L3bmR8");
		con.setRequestProperty("Authorization", "key=AIzaSyBhsCo3AiV4PF5KwM9Pli678fcsg6RbzfY");
		con.setRequestMethod("POST");

		JSONObject notification   = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject request = new JSONObject();
		
		notification.put("title","Task Centauro Solutions");
		notification.put("body", "Le informa que tiene una tarea por finalizar");
		notification.put("icon", "myicon");
		notification.put("sound","R.drawable.ic_stat_ic_notification");
		data.put("data", listId);
		request.put("to", token);
		request.put("notification", notification);
		request.put("data", data);

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(request.toString());
		wr.flush();

		//display what returns the POST request

		StringBuilder sb = new StringBuilder();  
		int HttpResult = con.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
		    BufferedReader br = new BufferedReader(
		            new InputStreamReader(con.getInputStream(), "utf-8"));
		    String line = null;  
		    while ((line = br.readLine()) != null) {  
		        sb.append(line + "\n");  
		    }
		    br.close();
		    System.out.println("" + sb.toString());  
		} else {
		    System.out.println(con.getResponseMessage());  
		}  
	}
	
	@RequestMapping("/haveNotification")
    public  String Notification() {
		String response = "No hay notificaciones pendientes";
		Timestamp maxDate = new Timestamp(System.currentTimeMillis()+60*60*1000);
 		List<CalendarModel> calendars = calendarService.getAllNotification(maxDate);
 		CalendarModel deleteCalendar = null;
		//List<CalendarModel> calendars = calendarService.findAll();
			if(calendars.size() > 0 ){
					for(CalendarModel calendar : calendars){
						List<UserModel> users = userService.findByEmail(calendar.getUser_id().getEmail());
						for(UserModel user : users){
							try {
								
								Message(user.getToken(),calendar.getList());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						 calendar.setFinishCalendar("1");
						 try {
							calendarService.update(calendar);
						} catch (ShopNotFound e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						
						response = "Se notifico correctamente";
				}
			}
		
		
		 return response;
		
	}
	
	@RequestMapping("/haveCalendar")
    public  Boolean haveCalendar(HttpServletRequest request) {
		boolean response = false;
		String listId = request.getParameter("listId");
		CalendarModel calendars = calendarService.existCalendarByList(listId);
 		CalendarModel deleteCalendar = null;
		//List<CalendarModel> calendars = calendarService.findAll();
			if(calendars != null ){
				response = true;
			}
	
		 return response;
		
	}
}
