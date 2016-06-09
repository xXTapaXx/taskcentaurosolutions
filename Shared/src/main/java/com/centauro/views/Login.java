package com.centauro.views;


public class Login {

	
    private final String username;
    private final String password;
    private final Object token;

    public Login(){
    	username = null;
    	password = null;
    	token = null;
    }
    
    public Login(String username, String password, Object token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public Object getToken() {
        return token;
    }
    
    @Override
    public String toString() {
        return "Value{" +
                "username=" + username +
                ", password='" + password + 
                ", token='" + token + '\'' +
                '}';
    }
}