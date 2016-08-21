package com.centauro.service;

import java.sql.Timestamp;
import java.util.List;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.CalendarModel;


public interface CalendarService {
	
	public CalendarModel create(CalendarModel calendar);
	public CalendarModel existCalendarByList(String list);
	public List<CalendarModel> getAllNotification(Timestamp maxDate);
	public CalendarModel delete(int id)  throws ShopNotFound;
	public CalendarModel update(CalendarModel list) throws ShopNotFound;
	public List<CalendarModel> findAll();
	//public CalendarModel findById(int id);
}
