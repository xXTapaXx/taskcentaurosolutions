package com.centauro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.FromLocationModel;
import com.centauro.model.LocationModel;
import com.centauro.model.MenuModel;



public interface FromLocationRepository extends JpaRepository<FromLocationModel, Integer> {
	
	
	/*@Query("SELECT f FROM FromLocationModel f INNER JOIN f.id_locations l  where f.id_locations.id = l.id ")
	  public List<FromLocationModel> findAllFromLocation();*/
	
	@Query("SELECT f FROM FromLocationModel f INNER JOIN f.id_locations l  where f.id_locations = :location ")
	  public List<FromLocationModel> findLocations(@Param("location") LocationModel location);
	
	 
}
