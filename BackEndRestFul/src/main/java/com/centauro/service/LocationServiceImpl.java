package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.LocationModel;
import com.centauro.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Resource
	private LocationRepository locationRepository;

	@Override
	@Transactional
	public LocationModel create(LocationModel location) {
		LocationModel createLocation = location;
		return locationRepository.save(createLocation);
	}

	@Override
	@Transactional
	public LocationModel findById(int id) {
		return locationRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public LocationModel delete(int id) throws ShopNotFound {
		LocationModel deletelocation = locationRepository.findOne(id);

		if (deletelocation == null)
			throw new ShopNotFound();

		locationRepository.delete(deletelocation);
		return deletelocation;
	}

	@Override
	@Transactional
	public List<LocationModel> findAll() {
		return locationRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public LocationModel update(LocationModel location) throws ShopNotFound {
		LocationModel updateLocation = locationRepository.findOne(location.getId());

		if (updateLocation == null)
			throw new ShopNotFound();

		updateLocation.setName(location.getName());

		return updateLocation;
	}

}
