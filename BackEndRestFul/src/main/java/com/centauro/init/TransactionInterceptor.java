package com.centauro.init;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TransactionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	
		throws Exception{
		HttpSession session = request.getSession(false);
		if(session.getAttribute("user")==null) {
			System.out.println("Need Login");
        }else
        {
        	System.out.println("Got request to save data: name:"+session.getAttribute("user"));
        	
        }
		
			
			return true;
		}
	
}
