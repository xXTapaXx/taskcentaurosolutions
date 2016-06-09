package com.centauro.views;


public class TaskView {

    private final String id;
    private final String content;

    public TaskView(){
    	id = null;
    	content = null;
    }
    
    public TaskView(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
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