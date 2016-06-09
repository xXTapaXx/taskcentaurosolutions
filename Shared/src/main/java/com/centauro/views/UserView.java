package com.centauro.views;


public class UserView {

    private final long id;
    private final String username;
    private final String password;

    public UserView(){
    	id = 0;
    	username = null;
    	password = null;
    }
    
    public UserView(long id, String username,String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    @Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", username='" + username + 
                ", password='" + password + '\'' +
                '}';
    }
}