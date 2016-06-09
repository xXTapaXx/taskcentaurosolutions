package com.centauro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;


public interface PruebaRepository extends JpaRepository<PruebaModel, Integer> {

}
