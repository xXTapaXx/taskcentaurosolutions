package com.centauro.controllers;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.*;
import com.google.api.services.tasks.Tasks;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class TasksQuickstart {
	
	//private static final String  URL_CALLBACK = "http://tasks-frontendweb.us-west-2.elasticbeanstalk.com/callBack";
		private static final String URL_CALLBACK = "http://localhost:8080/callBack";
	/** Application name. */
    private static final String APPLICATION_NAME =
        "Google Tasks API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/tasks-java-quickstart.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    
    public static AuthorizationCodeInstalledApp authorizationCodeInstalledApp;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/tasks-java-quickstart.json
     */
    private static final List<String> SCOPES =
        Arrays.asList(TasksScopes.TASKS_READONLY, TasksScopes.TASKS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(String code) throws IOException {
        // Load client secrets.
        InputStream in =
            TasksQuickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                		JSON_FACTORY,
                		clientSecrets,
                        SCOPES)
                //.setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                //.setAccessType("online")
                .build();
       
        GoogleAuthorizationCodeTokenRequest tokenRequest =
        		flow.newTokenRequest(code);
        	  tokenRequest.setRedirectUri(URL_CALLBACK);
        	  GoogleTokenResponse tokenResponse = tokenRequest.execute();

        	  // Store the credential for the user.
        	  
        return flow.createAndStoreCredential(tokenResponse, null);
        

    }

    /**
     * Build and return an authorized Tasks client service.
     * @return an authorized Tasks client service
     * @throws IOException
     */
    public static void getTasksService(HttpServletRequest request) throws IOException {
    	String code = (String) request.getSession().getAttribute("code");
        Credential credential = authorize(code);
        request.getSession().setAttribute("access_token", credential.getAccessToken());
        Tasks task = new Tasks.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        request.getSession().setAttribute("service", task);
    }
}