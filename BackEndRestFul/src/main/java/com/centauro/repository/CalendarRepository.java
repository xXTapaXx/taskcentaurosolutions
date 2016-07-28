package com.centauro.repository;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.CalendarModel;


public interface CalendarRepository extends JpaRepository<CalendarModel, Integer> {
	
	 /* @Query("select u from users u where u.username = :username")
	  public User findByUsername(String username);*/
	 
	@Query("SELECT c FROM CalendarModel c INNER JOIN c.user_id u where c.list = :list")
	    public CalendarModel existCalendarByList(@Param("list") String list);
	
	@Query("SELECT c FROM CalendarModel c INNER JOIN c.user_id u where CONVERT_TZ(CURRENT_TIMESTAMP, 'UTC', 'America/Costa_Rica') <= c.date AND c.date <= :maxDate ")
	  	public List<CalendarModel> getAllNotification(@Param("maxDate") Timestamp maxDate);
		
}
