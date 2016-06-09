package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ShopModel;
import com.centauro.repository.ShopRepository;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Resource
	private ShopRepository shopRepository;

	@Override
	@Transactional
	public ShopModel create(ShopModel shop) {
		ShopModel createdShop = shop;
		return shopRepository.save(createdShop);
	}
	
	@Override
	@Transactional
	public ShopModel findById(int id) {
		return shopRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public ShopModel delete(int id) throws ShopNotFound {
		ShopModel deletedShop = shopRepository.findOne(id);
		
		if (deletedShop == null)
			throw new ShopNotFound();
		
		shopRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	@Transactional
	public List<ShopModel> findAll() {
		return shopRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public ShopModel update(ShopModel shop) throws ShopNotFound {
		ShopModel updatedShop = shopRepository.findOne(shop.getId());
		
		if (updatedShop == null)
			throw new ShopNotFound();
		
		updatedShop.setName(shop.getName());
		updatedShop.setEmplNumber(shop.getEmplNumber());
		return updatedShop;
	}

}
