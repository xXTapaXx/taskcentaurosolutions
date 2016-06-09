package com.centauro.controllers;

import java.awt.Menu;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.centauro.views.ItemMenuView;
import com.centauro.views.Login;
import com.centauro.views.MenuView;
import com.centauro.views.UserView;


@Controller
public class ItemMenuController {
	
	@RequestMapping("/item-menu")
    public String listItemMenu(Model model) {
		try{
		RestTemplate restTemplate = new RestTemplate();
        List<ItemMenuView> item_Menu = restTemplate.getForObject("http://localhost:8081/item-menu", List.class);
        model.addAttribute("welcome", "welcome");
        model.addAttribute("itemMenus", item_Menu);
       
		
		}
		catch (Exception e) 
		{
			 System.out.println("Error: " + e);  
		} 
		return "ItemMenu/index";
	}

	@RequestMapping("/item-menuCreate")
    public String create(Model model) {
		 RestTemplate restTemplate = new RestTemplate();
		 List<Menu> menu = restTemplate.getForObject("http://localhost:8081/item-menuCreate", List.class);
	        model.addAttribute("menus", menu);
     
		return "ItemMenu/create";
	}
	
	 @RequestMapping("/item-menu/{id}/edit")
	    public String editItemMenu(@PathVariable("id")int id,Model model)  {   	
	    	
		 RestTemplate restTemplate = new RestTemplate();
		 ItemMenuView item_Menu = restTemplate.getForObject("http://localhost:8081/item-menu/{id}/edit", ItemMenuView.class,id);
		 List<Menu> menu = restTemplate.getForObject("http://localhost:8081/item-menuCreate", List.class); 
		 ArrayList<String> type = new ArrayList<String>();
		 type.add("menu");
		 type.add("item");
		 model.addAttribute("itemMenu", item_Menu);
		 model.addAttribute("menus",menu);
		 model.addAttribute("types", type);
	        model.addAttribute("url", "/item-menu/" + item_Menu.getId()+ "/update");
	        return "ItemMenu/edit";    
	    
	    }
	  
	@RequestMapping(value = "/saveItem-menu", method = RequestMethod.POST)
	 public String saveItemMenu(HttpServletRequest request, Model model) {
			try{
				
				
				String title = request.getParameter("title");
				String alias = request.getParameter("alias");
			    String type = request.getParameter("type");
			    Integer id_menu = Integer.parseInt("" + request.getParameter("id_menu"));
				RestTemplate restTemplate = new RestTemplate();
		       ItemMenuView item_Menu = restTemplate.getForObject("http://localhost:8081/saveItem-menu?title={title}&alias={alias}&type={type}&id_menu={id_menu}", ItemMenuView.class,title,alias,type,id_menu);
		       // UserView user = restTemplate.postForObject("http://localhost:8081/saveUser", UserView.class, UserView.class);	
			model.addAttribute("welcome", "welcome");
			model.addAttribute("itemMenu", item_Menu);
			
			}
			catch (Exception e) 
			{
				 System.out.println("Error: " + e);  
			} 
			return "redirect:/item-menu";
		}
	
	
	@RequestMapping(value = "/item-menu/{id}/update", method = RequestMethod.POST)
	 public String updateItemMenu(@PathVariable(value="id") int id,HttpServletRequest request, Model model) {
			try{
				
				String title = request.getParameter("title");
				String alias = request.getParameter("alias");
			    String type = request.getParameter("type");
			    Integer id_menu = Integer.parseInt("" + request.getParameter("id_menu"));
				RestTemplate restTemplate = new RestTemplate();
		       ItemMenuView item_Menu = restTemplate.getForObject("http://localhost:8081/item-menu/{id}/update?title={title}&alias={alias}&type={type}&id_menu={id_menu}", ItemMenuView.class,id,title,alias,type,id_menu);
		       // UserView user = restTemplate.postForObject("http://localhost:8081/saveUser", UserView.class, UserView.class);	
			model.addAttribute("welcome", "welcome");
			model.addAttribute("itemMenu", item_Menu);
			
			}
			catch (Exception e) 
			{
				 System.out.println("Error: " + e);  
			} 
			return "redirect:/item-menu";
		}
	
	@RequestMapping(value = "/item-menu/{id}/delete", method = RequestMethod.POST)
	 public String deleteItemMenu(@PathVariable(value="id") int id, Model model) {
			try{
				
				
				RestTemplate restTemplate = new RestTemplate();
		        String item_Menu = restTemplate.getForObject("http://localhost:8081/item-menu/{id}/delete", String.class,id);
		        	
			model.addAttribute("welcome", "welcome");
			model.addAttribute("itemMenu", item_Menu);
			
			}
			catch (Exception e) 
			{
				 System.out.println("Error: " + e);  
			} 
			return "redirect:/item-menu";
		}
	 
	
}
