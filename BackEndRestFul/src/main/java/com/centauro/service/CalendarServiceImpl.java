package com.centauro.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.model.CalendarModel;
import com.centauro.repository.CalendarRepository;


@Service
public class CalendarServiceImpl implements CalendarService {
	
	@Resource
	private CalendarRepository calendarRepository;

	@Override
	@Transactional
	public CalendarModel create(CalendarModel calendarModel) {
		CalendarModel createdCalendar = calendarModel;
		return calendarRepository.save(createdCalendar);
	}

	@Override
	public CalendarModel existCalendarByList(String list) {
		// TODO Auto-generated method stub
		return calendarRepository.existCalendarByList(list);
	}

	@Override
	public List<CalendarModel> getAllNotification(Timestamp maxDate) {
		// TODO Auto-generated method stub
		return calendarRepository.getAllNotification(maxDate);
	}

	@Override
	public List<CalendarModel> findAll() {
		// TODO Auto-generated method stub
		return calendarRepository.findAll();
	}
	
}
