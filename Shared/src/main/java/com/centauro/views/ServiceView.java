package com.centauro.views;

import java.awt.Menu;
import java.sql.Timestamp;
import java.util.List;

public class ServiceView {

    private final long id;
    private final String service;


    
    public ServiceView(){
    	id = 0;
    	service = null;


    }
    
    public ServiceView(long id, String service) {
        this.id = id;
        this.service = service;

    }

   
    
   
	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", service='" + service + '\'' +
                '}';
    }

	public long getId() {
		return id;
	}

	public String getService() {
		return service;
	}

	

	
}