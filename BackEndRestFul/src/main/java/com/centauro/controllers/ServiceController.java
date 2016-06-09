package com.centauro.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.LocationModel;
import com.centauro.model.PruebaModel;
import com.centauro.model.ServiceModel;
import com.centauro.model.UserModel;
import com.centauro.service.LocationService;
import com.centauro.service.PruebaService;
import com.centauro.service.ServiceService;
import com.centauro.service.UserService;
import com.centauro.views.Admin;
import com.centauro.views.LocationView;
import com.centauro.views.Login;
import com.centauro.views.ServiceView;
import com.centauro.views.UserView;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
@RestController
public class ServiceController {
	

	private final AtomicLong counter = new AtomicLong();
	
    @Autowired
	private ServiceService serviceService;
    
    @RequestMapping("/service")
    public List<ServiceModel> getAllService() throws UnsupportedEncodingException, GeneralSecurityException {
    	
        return serviceService.findAll();
        
    }
    
    @RequestMapping("/service/{id}/edit")
    public ServiceModel editService(@PathVariable("id")int id) throws UnsupportedEncodingException, GeneralSecurityException {   	
    	
    	return serviceService.findById(id);    
    
    }
    
    @RequestMapping("/saveService")
    public ServiceView saveService(HttpServletRequest request) throws UnsupportedEncodingException, GeneralSecurityException, ShopNotFound {
    	
    	ServiceModel service = new ServiceModel();
    	service.setService(request.getParameter("service"));

    	
    	serviceService.create(service);
    	  	
        return new ServiceView(counter.incrementAndGet(),service.getService());
    }
    
    @RequestMapping("/service/{id}/update")
    public ServiceView updateService(@PathVariable(value="id") int id,HttpServletRequest request) throws UnsupportedEncodingException, GeneralSecurityException {
    	
    	ServiceModel service = serviceService.findById(id);
    	service.setService(request.getParameter("service"));

    	try {
			serviceService.update(service);
		} catch (ShopNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ServiceView(counter.incrementAndGet(),service.getService());
    }
    
    @RequestMapping("/service/{id}/delete")
    public String deleteService(@PathVariable(value="id") int id) throws UnsupportedEncodingException, GeneralSecurityException {
    	   	
		try {
			serviceService.delete(id);
		} catch (ShopNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return "Menu Eliminado";
    }
    

   

}