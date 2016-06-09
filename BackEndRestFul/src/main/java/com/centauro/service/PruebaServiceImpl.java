package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.PruebaModel;
import com.centauro.model.ShopModel;
import com.centauro.repository.PruebaRepository;
import com.centauro.repository.ShopRepository;

@Service
public class PruebaServiceImpl implements PruebaService {
	
	@Resource
	private PruebaRepository pruebaRepository;

	@Override
	@Transactional
	public PruebaModel create(PruebaModel shop) {
		PruebaModel createdShop = shop;
		return pruebaRepository.save(createdShop);
	}
	
	@Override
	@Transactional
	public PruebaModel findById(int id) {
		return pruebaRepository.findOne(id);
	}
	
	

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public PruebaModel delete(int id) throws ShopNotFound {
		PruebaModel deletedShop = pruebaRepository.findOne(id);
		
		if (deletedShop == null)
			throw new ShopNotFound();
		
		pruebaRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<PruebaModel> findAll() {
		return pruebaRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public PruebaModel update(PruebaModel shop) throws ShopNotFound {
		PruebaModel updatedShop = pruebaRepository.findOne(shop.getId());
		
		if (updatedShop == null)
			throw new ShopNotFound();
		
		updatedShop.setName(shop.getName());
		updatedShop.setArtNumber(shop.getArtNumber());
		return updatedShop;
	}

}
