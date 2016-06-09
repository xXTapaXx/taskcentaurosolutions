package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.FromLocationModel;
import com.centauro.model.LocationModel;
import com.centauro.model.ProductModel;
import com.centauro.model.ServiceModel;
import com.centauro.repository.FromLocationRepository;
import com.centauro.repository.LocationRepository;
import com.centauro.repository.ProductRepository;
import com.centauro.repository.ServiceRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductRepository productRepository;

	@Override
	@Transactional
	public ProductModel create(ProductModel fromLocation) {
		ProductModel createProduct = fromLocation;
		return productRepository.save(createProduct);
	}

	@Override
	@Transactional
	public ProductModel findById(int id) {
		return productRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public ProductModel delete(int id) throws ShopNotFound {
		ProductModel deleteProduct = productRepository.findOne(id);

		if (deleteProduct == null)
			throw new ShopNotFound();

		productRepository.delete(deleteProduct);
		return deleteProduct;
	}

	@Override
	@Transactional
	public List<ProductModel> findAll() {
		return productRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ShopNotFound.class)
	public ProductModel update(ProductModel product) throws ShopNotFound {
		ProductModel updateProduct = productRepository.findOne(product.getId());

		if (updateProduct == null)
			throw new ShopNotFound();

		updateProduct.setId_from_location(product.getId_from_location());
		updateProduct.setId_to_location(product.getId_to_location());
		updateProduct.setId_service(product.getId_service());
		updateProduct.setPrice(product.getPrice());

		return updateProduct;
	}

}
