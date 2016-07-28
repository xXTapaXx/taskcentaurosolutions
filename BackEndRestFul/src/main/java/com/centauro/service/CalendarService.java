package com.centauro.service;

import java.sql.Timestamp;
import java.util.List;

import com.centauro.model.CalendarModel;


public interface CalendarService {
	
	public CalendarModel create(CalendarModel calendar);
	public CalendarModel existCalendarByList(String list);
	public List<CalendarModel> getAllNotification(Timestamp maxDate);
	public List<CalendarModel> findAll();
	/*public ListModel delete(int id) throws ShopNotFound;
	public List<ListModel> findAll();
	public ListModel update(ListModel list) throws ShopNotFound;
	public ListModel findById(int id);*/
}
