package com.centauro.views;


public class Admin {

    private final long id;
    private final String content;

    public Admin(){
    	id = 0;
    	content = null;
    }
    
    public Admin(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
    
    @Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}