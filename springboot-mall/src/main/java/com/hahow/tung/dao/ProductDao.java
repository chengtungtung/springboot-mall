package com.hahow.tung.dao;

import java.util.List;

import com.hahow.tung.constant.ProductCategory;
import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;

public interface ProductDao {

	Product getProductById(Integer productId);
	Integer createProduct(ProductRequest productRequest);
	void updateProduct(Integer productId, ProductRequest productRequest); 
	Product deleteProductById(Integer productId);
	List<Product> getProducts(ProductCategory category, String search);
}
