package com.hahow.tung.service;

import java.util.List;

import com.hahow.tung.dto.ProductQueryParams;
import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;

public interface ProductService {

	Product getProductById(Integer productId);

	Integer createProduct(ProductRequest productRequest);

	void updateProduct(Integer productId, ProductRequest productRequest);
	
	Product deleteProductById(Integer productId);
	
	// 方法一 ： 較難維護
//	List<Product> getProducts(ProductCategory category, String search);
	// 方法二 ： 較好維護
	List<Product> getProducts(ProductQueryParams productQueryParams);
	
	Integer countProduct(ProductQueryParams productQueryParams);

}
