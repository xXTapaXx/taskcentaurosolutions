package com.centauro.views;


public class PageView {

    private final long id;
    private final String title;
    private final String alias;
    private final String type;
    private final Integer id_type;
    private final String position;
    private final String item;
    
    public PageView(){
    	id = 0;
    	title = null;
    	alias = null;
    	type = null;
    	id_type = 0;
    	position = null;
    	item = null;
    }
    
    public String getItem() {
		return item;
	}

	public PageView(long id, String title,String alias,String type,Integer id_type, String position, String item) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.type = type;
        this.id_type = id_type;
        this.position = position;
        this.item = item;
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

	public Integer getId_type() {
		return id_type;
	}

	public String getPosition() {
		return position;
	}

	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", title='" + title + 
                ", alias='" + alias + 
                ", type='" + type +
                ", id_type='" + id_type +
                ", position='" + position +
                ", item='" + item +'\'' +
                '}';
    }
}