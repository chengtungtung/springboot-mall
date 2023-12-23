package com.hahow.tung.dao;

import java.util.List;

import com.hahow.tung.dto.ProductQueryParams;
import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;

public interface ProductDao {

	Product getProductById(Integer productId);

	Integer createProduct(ProductRequest productRequest);

	void updateProduct(Integer productId, ProductRequest productRequest);

	Product deleteProductById(Integer productId);

	// 方法一 ： 較難維護
//	List<Product> getProducts(ProductCategory category, String search);
	// 方法二 ： 較好維護
	List<Product> getProducts(ProductQueryParams productQueryParams);
}
