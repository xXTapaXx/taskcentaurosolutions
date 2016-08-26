package com.centauro.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.ImmutableMap;


@Controller
public class LoginController {
	
	private final String clientId = "992860120359-if29j9eqar7op5f8f02s8p4fqci7dfpk.apps.googleusercontent.com";
	private final String clientSecret = "QjnnKJjXBQhSrwO5kNsp16wr";
	private final String URL_GOOGLE = "https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile%20https://www.googleapis.com/auth/tasks%20https://www.googleapis.com/auth/tasks.readonly&redirect_uri=http://tasks-frontendweb.us-west-2.elasticbeanstalk.com/callBack&response_type=code&client_id=992860120359-if29j9eqar7op5f8f02s8p4fqci7dfpk.apps.googleusercontent.com&access_type=offline&approval_prompt=force";
	//private final String URL_GOOGLE = "https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile%20https://www.googleapis.com/auth/tasks%20https://www.googleapis.com/auth/tasks.readonly&redirect_uri=http://localhost:8080/callBack&response_type=code&client_id=992860120359-if29j9eqar7op5f8f02s8p4fqci7dfpk.apps.googleusercontent.com&access_type=offline&approval_prompt=force";
	private static final String URL_BACKEND = "http://tasks-dev.us-west-2.elasticbeanstalk.com/";
	//private static final String URL_BACKEND = "http://localhost:8081/";
		private static final String URI_EMAIL = "registerUser?email=";
		private static final String URI_TOKEN = "&token=";
	@RequestMapping( "/")
    public String Login(Model model) {
			return "login";
	}
	
	@RequestMapping("/signIn")
	public String SignIn(){
		return "redirect:"+URL_GOOGLE;
	}
	
	@RequestMapping(value = "/callBack")
    public String callBack(HttpServletRequest request,HttpServletResponse resp) {
    	@SuppressWarnings("unused")
		String code = request.getParameter("code");    
    	   // get the access token by post to Google
    	   String body = null;
    	   JSONObject jsonObject = null;
    	// get some info about the user with the access token
    	   String json = null;
    	   String registerUser = null;
    	   Map<String,String> formParameters = new HashMap();
		try {
			request.getSession().setAttribute("code", code);
			TasksQuickstart.getTasksService(request);

			// google tokens expire after an hour, but since we requested offline access we can get a new token without user involvement via the refresh token
	    	  
	    	   String accessToken = (String) request.getSession().getAttribute("access_token");
	    	   // you may want to store the access token in session
	    	   	   
	    	   json = get(new StringBuilder("https://www.googleapis.com/oauth2/v1/userinfo?access_token=").append(accessToken).toString());
	    	   
	    	   jsonObject = (JSONObject) new JSONParser().parse(json);
	    	   request.getSession().setAttribute("user", jsonObject);
	    	   String email = (String) jsonObject.get("email");
	    	   registerUser = post(new StringBuilder(URL_BACKEND).append(URI_EMAIL).append(URLEncoder.encode(email,"UTF-8")).append(URI_TOKEN).append(URLEncoder.encode(" ","UTF-8")).toString(),formParameters);
	    	   resp.getWriter().println(json);
	    	   
	    	   
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
    	    throw new RuntimeException("Unable to parse json " + body);
    	}
		
		return "redirect:/taskslists";
	
    }
	
	@RequestMapping(value = "/signOut")
    public String signOut(HttpServletRequest request,HttpServletResponse resp) {
    	
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("access_token");
		return "redirect:/taskslists";
    }
    
 // makes a GET request to url and returns body as a string
    public String get(String url) throws ClientProtocolException, IOException {
     return execute(new HttpGet(url));
    }
     
    // makes a POST request to url with form parameters and returns body as a string
    public String post(String url, Map<String,String> formParameters) throws ClientProtocolException, IOException { 
     HttpPost request = new HttpPost(url);
       
     List <NameValuePair> nvps = new ArrayList <NameValuePair>();
      
     if(formParameters.size() > 0 && formParameters != null){
    	 for (String key : formParameters.keySet()) {
    	      nvps.add(new BasicNameValuePair(key, formParameters.get(key))); 
    	     }
    	 request.setEntity(new UrlEncodedFormEntity(nvps));
     }
      
     return execute(request);
    }
     
    // makes request and checks response code for 200
    private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
     HttpClient httpClient = new DefaultHttpClient();
     HttpResponse response = httpClient.execute(request);
         
     HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
    
     if (response.getStatusLine().getStatusCode() != 200) {
      throw new RuntimeException("Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
     }
    
        return body;
    }
}
