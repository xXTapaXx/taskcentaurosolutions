package com.centauro.views;

import java.awt.Menu;
import java.sql.Timestamp;
import java.util.List;

public class ItemMenuView {

    private final long id;
    private final String title;
    private final String alias;
    private final String type;
    private final Object id_menu;

    
    public ItemMenuView(){
    	id = 0;
    	title = null;
    	alias = null;
    	type = null;
    	id_menu = null;

    }
    
    public ItemMenuView(long id, String title,String alias,String type,Object menu) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.type = type;
        this.id_menu = menu;
    }

   
    
   
	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", title='" + title + 
                ", alias='" + alias + 
                ", type='" + type +           
                ", id_menu='" + id_menu + '\'' +
                '}';
    }

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAlias() {
		return alias;
	}

	public String getType() {
		return type;
	}

	public Object getId_menu() {
		return id_menu;
	}
}