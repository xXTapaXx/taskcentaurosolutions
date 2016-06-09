package com.centauro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centauro.model.ShopModel;


public interface ShopRepository extends JpaRepository<ShopModel, Integer> {

}
