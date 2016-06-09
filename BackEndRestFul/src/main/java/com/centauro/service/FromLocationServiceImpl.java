package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.FromLocationModel;
import com.centauro.model.LocationModel;
import com.centauro.model.ServiceModel;
import com.centauro.repository.FromLocationRepository;
import com.centauro.repository.LocationRepository;
import com.centauro.repository.ServiceRepository;

@Service
public class FromLocationServiceImpl implements FromLocationService {

	@Resource
	private FromLocationRepository fromLocationRepository;

	@Override
	@Transactional
	public FromLocationModel create(FromLocationModel fromLocation) {
		FromLocationModel createFromLocation = fromLocation;
		return fromLocationRepository.save(createFromLocation);
	}
	
	@Override
	@Transactional
	public List<FromLocationModel> findLocations(LocationModel location){
		return fromLocationRepository.findLocations(location);
	}

	@Override
	@Transactional
	public FromLocationModel findById(int id) {
		return fromLocationRepository.findOne(id);
	}
	
	/*@Override
	@Transactional
	public List<FromLocationModel> findAllFromLocation(){
		return fromLocationRepository.findAllFromLocation();
	}*/

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public FromLocationModel delete(int id) throws ShopNotFound {
		FromLocationModel deleteFromLocation = fromLocationRepository.findOne(id);

		if (deleteFromLocation == null)
			throw new ShopNotFound();

		fromLocationRepository.delete(deleteFromLocation);
		return deleteFromLocation;
	}

	@Override
	@Transactional
	public List<FromLocationModel> findAll() {
		return fromLocationRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public FromLocationModel update(FromLocationModel fromLocation) throws ShopNotFound {
		FromLocationModel updateFromLocation = fromLocationRepository.findOne(fromLocation.getId());

		if (updateFromLocation == null)
			throw new ShopNotFound();

		updateFromLocation.setId_locations(fromLocation.getId_locations());
		updateFromLocation.setPlace(fromLocation.getPlace());

		return updateFromLocation;
	}

}
