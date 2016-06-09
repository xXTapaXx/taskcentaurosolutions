package com.centauro.views;

import java.awt.Menu;
import java.sql.Timestamp;
import java.util.List;

public class ProductView {

    private final long id;
    private final Object id_from_location;
    private final Object id_to_location;
    private final Object id_service;
    private final Double price;

    
    public ProductView(){
    	id = 0;
    	id_from_location = null;
    	id_to_location = null;
    	id_service = null;
    	price = 0.0;

    }
    
    public long getId() {
		return id;
	}

	public Object getId_from_location() {
		return id_from_location;
	}

	public Object getId_to_location() {
		return id_to_location;
	}

	public Object getId_service() {
		return id_service;
	}

	public Double getPrice() {
		return price;
	}

	public ProductView(long id, Object id_from_location,Object id_to_location,Object id_service,Double price) {
        this.id = id;
        this.id_from_location = id_from_location;
        this.id_to_location = id_to_location;
        this.id_service = id_service;
        this.price = price;
    }

   
    
   
	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", id_from_location='" + id_from_location + 
                ", id_to_location='" + id_to_location + 
                ", id_service='" + id_service +           
                ", price='" + price + '\'' +
                '}';
    }

	
}