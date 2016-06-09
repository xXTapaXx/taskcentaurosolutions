package com.centauro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "from_locations")
public class FromLocationModel {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne()
    @JoinColumn(name="id_locations", referencedColumnName ="id")
	private LocationModel id_locations;
	
	private String place;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocationModel getId_locations() {
		return id_locations;
	}

	public void setId_locations(LocationModel id_locations) {
		this.id_locations = id_locations;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	

	
}
