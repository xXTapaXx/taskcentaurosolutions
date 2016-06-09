package com.centauro.views;

import java.awt.Menu;
import java.sql.Timestamp;
import java.util.List;

public class FromLocationView {

    private final long id;
    private final Object id_locations;
    private final String place;


    
    public FromLocationView(){
    	id = 0;
    	id_locations = null;
    	place = null;


    }
    
    public FromLocationView(long id, Object id_locations,String place) {
        this.id = id;
        this.id_locations = id_locations;
        this.place = place;

    }

   
    
   
	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", id_locations='" + id_locations +            
                ", place='" + place + '\'' +
                '}';
    }

	public long getId() {
		return id;
	}

	public Object getId_locations() {
		return id_locations;
	}

	public String getPlace() {
		return place;
	}

	
}