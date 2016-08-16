package com.centauro.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

 

public class AuthInterceptor implements HandlerInterceptor {

 

	private static final Logger logger = Logger.getLogger("AuthInterceptor");

 

    @Override

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.info(" Pre handle ");

        	if (httpServletRequest.getSession().getAttribute("access_token") == null && !httpServletRequest.getRequestURI().endsWith("/")) {
        		httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");
                return false;               
            }
        	
        	return true;
           

    }



	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
