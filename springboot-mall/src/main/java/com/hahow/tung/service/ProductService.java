package com.hahow.tung.service;

import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;

public interface ProductService {

	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
}
