package com.hahow.tung.dao;

import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;

public interface ProductDao {

	Product getProductById(Integer productId);
	Integer createProduct(ProductRequest productRequest);
	void updateProduct(Integer productId, ProductRequest productRequest); 
}
