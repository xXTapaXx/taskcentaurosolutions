package com.centauro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class ProductModel {
   
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne()
    @JoinColumn(name="id_from_location", referencedColumnName ="id")
	private LocationModel id_from_location;
	
	@ManyToOne()
   @JoinColumn(name="id_to_location", referencedColumnName ="id")
	private LocationModel id_to_location;

	@ManyToOne()
    @JoinColumn(name="id_service", referencedColumnName ="id")
	private ServiceModel id_service;
	
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocationModel getId_from_location() {
		return id_from_location;
	}

	public void setId_from_location(LocationModel id_from_location) {
		this.id_from_location = id_from_location;
	}

	public LocationModel getId_to_location() {
		return id_to_location;
	}

	public void setId_to_location(LocationModel id_to_location) {
		this.id_to_location = id_to_location;
	}

	public ServiceModel getId_service() {
		return id_service;
	}

	public void setId_service(ServiceModel id_service) {
		this.id_service = id_service;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	

	

	
	
	
}
