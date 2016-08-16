package com.centauro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TransactionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	
		throws Exception{
		HttpSession session = request.getSession(false);
		if(session.getAttribute("access_token")==null) {
			return false;
        }else
        {
        	System.out.println("Got request to save data: name:"+session.getAttribute("access_token"));
        	
        }
		
			
			return true;
		}
	
}
