package com.hahow.tung.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahow.tung.dao.ProductDao;
import com.hahow.tung.dto.ProductQueryParams;
import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;
import com.hahow.tung.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public Product getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		return productDao.createProduct(productRequest);
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		productDao.updateProduct(productId, productRequest);
		
	}

	@Override
	public Product deleteProductById(Integer productId) {
		return productDao.deleteProductById(productId);
	}


	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		return productDao.getProducts(productQueryParams);
	}

	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		return productDao.countProduct(productQueryParams);
	}
	
	
	
	
	
	

}
