package com.centauro.controllers;

import java.awt.Menu;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.unbescape.html.HtmlEscape;

//import com.centauro.model.User;
import com.centauro.views.Admin;
import com.centauro.views.ArticleView;
import com.centauro.views.LocationView;
import com.centauro.views.Login;
import com.centauro.views.MenuView;
import com.centauro.views.ServiceView;
import com.centauro.views.UserView;


@Controller
public class ServiceController {
	
	
	
	@RequestMapping("/service")
    public String listService(Model model) {
		try{
		RestTemplate restTemplate = new RestTemplate();
        List<ServiceView> service = restTemplate.getForObject("http://localhost:8081/service", List.class);
        model.addAttribute("services", service);
       
		}
		catch (Exception e) 
		{
			 System.out.println("Error: " + e);  
		} 
		return "service/index";
	}
	
	@RequestMapping("/serviceCreate")
    public String login(Model model) {
		return "service/create";
	}
	
	 @RequestMapping("/service/{id}/edit")
	    public String editService(@PathVariable("id")int id,Model model)  {   	
	    	
		 RestTemplate restTemplate = new RestTemplate();
		 ServiceView service = restTemplate.getForObject("http://localhost:8081/service/{id}/edit", ServiceView.class,id);
	        model.addAttribute("service", service);
	        model.addAttribute("url", "/service/" + service.getId()+ "/update");
	        return "service/edit";    
	    
	    }
	 
	 
	@RequestMapping(value = "/saveService", method = RequestMethod.POST)
	 public String createService(HttpServletRequest request, Model model) {
			try{
				
				
				String service = request.getParameter("service");
				
				RestTemplate restTemplate = new RestTemplate();
				ServiceView serviceResult = restTemplate.getForObject("http://localhost:8081/saveService?service={service}", ServiceView.class,service);
		       // UserView user = restTemplate.postForObject("http://localhost:8081/saveUser", UserView.class, UserView.class);	

			model.addAttribute("service", serviceResult);
			
			}
			catch (Exception e) 
			{
				 System.out.println("Error: " + e);  
			} 
			return "redirect:/service";
		}
	
	@RequestMapping(value = "/service/{id}/update", method = RequestMethod.POST)
	 public String updateService(@PathVariable(value="id") int id,HttpServletRequest request, Model model) {
			try{
				
				String service = request.getParameter("service");
				
				RestTemplate restTemplate = new RestTemplate();
				ServiceView serviceResult = restTemplate.getForObject("http://localhost:8081/service/{id}/update?service={service}", ServiceView.class,id,service);
		     
			model.addAttribute("service", serviceResult);
			
			}
			catch (Exception e) 
			{
				 System.out.println("Error: " + e);  
			} 
			return "redirect:/service";
		}
	
	@RequestMapping(value = "/service/{id}/delete", method = RequestMethod.POST)
	 public String deleteService(@PathVariable(value="id") int id, Model model) {
			try{
				
				
				RestTemplate restTemplate = new RestTemplate();
		        String service = restTemplate.getForObject("http://localhost:8081/service/{id}/delete", String.class,id);
		        	
		        model.addAttribute("service", service);
			
			}
			catch (Exception e) 
			{
				 System.out.println("Error: " + e);  
			} 
			return "redirect:/service";
		}
	 
	
}
