package com.centauro.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {

    private static final SimpleDateFormat dateFormat = 
        new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    
    private static final String URL_BACKEND = "http://tasks-dev.us-west-2.elasticbeanstalk.com/";
  	//private static final String URL_BACKEND = "http://localhost:8081/";
  	 
    @Scheduled(fixedRate = 10000)
    public void sendNotificationMessage() {
    	BufferedReader in = null;
    	String url = URL_BACKEND + "haveNotification";
    	String inputLine = null;
		StringBuffer response = new StringBuffer();
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			//con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			
			

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		//print result
		System.out.println(response.toString());
    	
    	//String response = messageController.Notification();
        System.out.println("sendNotificationMessage at " 
            + dateFormat.format(new Date()));
        
       /* System.out.println("Response: " 
                + response);*/

    }
}
