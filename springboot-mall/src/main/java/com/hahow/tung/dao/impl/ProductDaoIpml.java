package com.hahow.tung.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.hahow.tung.constant.ProductCategory;
import com.hahow.tung.dao.ProductDao;
import com.hahow.tung.dto.ProductQueryParams;
import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;
import com.hahow.tung.rowmapper.ProductRowMapper;

@Component
public class ProductDaoIpml implements ProductDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Product getProductById(Integer productId) {
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id = :productId";
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);

		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

		if (productList.size() > 0) {
			return productList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		String sql = "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date)"
				+ "VALUES(:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";

		Map<String, Object> map = new HashMap<>();
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().toString());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());

		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);

		// 當id值會自動生成的做法，可以取得id值
		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

		int productId = keyHolder.getKey().intValue();

		return productId;
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl,"
				+ "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate "
				+ "WHERE product_id = :productId";

		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);

		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().toString());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());

		map.put("lastModifiedDate", new Date());

		namedParameterJdbcTemplate.update(sql, map);

	}

	@Override
	public Product deleteProductById(Integer productId) {
		String sql = "DELETE FROM product WHERE product_id = :productId";

		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);

		Product delProduct = getProductById(productId);

		namedParameterJdbcTemplate.update(sql, map);

		return delProduct;
	}

	// 方法一 ： 較難維護
//	@Override
//	public List<Product> getProducts(ProductCategory category, String search) {
//		String sql = "SELECT product_id, product_name, category, image_url, price, stock, "
//				+ "description, created_date, last_modified_date FROM product WHERE 1=1";
//
//		Map<String, Object> map = new HashMap<>();
//
//		if (category != null) {
//			sql = sql + " AND category = :category";
//			map.put("category", category.name());
//		}
//		if (search != null) {
//			sql = sql + " AND product_name LIKE :search";
//			map.put("search", "%" + search + "%");
//
//			// 錯誤寫法，%不能寫在SQL語句裡面
////			sql = sql + " AND product_name LIKE %:search%";
////			map.put("search", search);
//		}
//		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
//		return productList;
//	}
	
	// 方法二 ：較好維護
	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, "
				+ "description, created_date, last_modified_date FROM product WHERE 1=1";
		
		Map<String, Object> map = new HashMap<>();
		
		if (productQueryParams.getCategory() != null) {
			sql = sql + " AND category = :category";
			map.put("category", productQueryParams.getCategory().name());
		}
		if (productQueryParams.getSearch() != null) {
			sql = sql + " AND product_name LIKE :search";
			map.put("search", "%" + productQueryParams.getSearch() + "%");
		}
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		
		return productList;
	}

}
