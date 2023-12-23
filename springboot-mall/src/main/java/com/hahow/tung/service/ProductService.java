package com.hahow.tung.service;

import java.util.List;

import com.hahow.tung.constant.ProductCategory;
import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;

public interface ProductService {

	Product getProductById(Integer productId);

	Integer createProduct(ProductRequest productRequest);

	void updateProduct(Integer productId, ProductRequest productRequest);
	
	Product deleteProductById(Integer productId);
	
	List<Product> getProducts(ProductCategory category, String search);

}
