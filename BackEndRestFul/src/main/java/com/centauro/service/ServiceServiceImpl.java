package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.LocationModel;
import com.centauro.model.ServiceModel;
import com.centauro.repository.LocationRepository;
import com.centauro.repository.ServiceRepository;

@Service
public class ServiceServiceImpl implements ServiceService {

	@Resource
	private ServiceRepository serviceRepository;

	@Override
	@Transactional
	public ServiceModel create(ServiceModel service) {
		ServiceModel createService = service;
		return serviceRepository.save(createService);
	}

	@Override
	@Transactional
	public ServiceModel findById(int id) {
		return serviceRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public ServiceModel delete(int id) throws ShopNotFound {
		ServiceModel deleteService = serviceRepository.findOne(id);

		if (deleteService == null)
			throw new ShopNotFound();

		serviceRepository.delete(deleteService);
		return deleteService;
	}

	@Override
	@Transactional
	public List<ServiceModel> findAll() {
		return serviceRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public ServiceModel update(ServiceModel service) throws ShopNotFound {
		ServiceModel updateService = serviceRepository.findOne(service.getId());

		if (updateService == null)
			throw new ShopNotFound();

		updateService.setService(service.getService());

		return updateService;
	}

}
