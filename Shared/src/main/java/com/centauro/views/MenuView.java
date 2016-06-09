package com.centauro.views;


public class MenuView {

    private final long id;
    private final String title;
    private final String alias;
    private final Integer father;
    private final Integer son;
    
    public MenuView(){
    	id = 0;
    	title = null;
    	alias = null;
    	father = 0;
    	son = 0;
    }
    
    public MenuView(long id, String title,String alias,Integer father,Integer son) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.father = father;
        this.son = son;
    }

   
    
    public Integer getSon() {
		return son;
	}

	public Integer getFather() {
		return father;
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

	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", title='" + title + 
                ", alias='" + alias + 
                ", father='" + father +
                ", son='" + son + '\'' +
                '}';
    }
}