package com.centauro.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.LocationModel;



public interface LocationRepository extends JpaRepository<LocationModel, Integer> {
	
	
	  
}
