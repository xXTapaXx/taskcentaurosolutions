package com.centauro.views;

import java.awt.Menu;
import java.sql.Timestamp;
import java.util.List;

public class LocationView {

    private final long id;
    private final String name;


    
    public LocationView(){
    	id = 0;
    	name = null;


    }
    
    public LocationView(long id, String name) {
        this.id = id;
        this.name = name;

    }

   
    
   
	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	
}