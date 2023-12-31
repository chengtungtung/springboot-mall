package com.hahow.tung.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hahow.tung.constant.ProductCategory;
import com.hahow.tung.dto.ProductQueryParams;
import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;
import com.hahow.tung.service.ProductService;
import com.hahow.tung.util.Page;

@Validated
@RestController
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	// 查詢單個商品
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
		log.info("進入ProductController的getProduct方法");

		Product product = productService.getProductById(productId);

		if (product != null) {
			return ResponseEntity.status(HttpStatus.OK).body(product);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	// 新增商品
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
		log.info("進入ProductController的createProduct方法");

		Integer productId = productService.createProduct(productRequest);

		Product product = productService.getProductById(productId);

		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	// 修改商品
	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
			@RequestBody @Valid ProductRequest productRequest) {
		log.info("進入ProductController的updateProduct方法 => 修改的productId值為 " + productId);
		Product product = productService.getProductById(productId);

		if (product != null) {
			productService.updateProduct(productId, productRequest);
			Product updateProduct = productService.getProductById(productId);
			return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// 刪除商品
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
		log.info("進入ProductController的deleteProduct方法 => 刪除的productId值為 " + productId);

		Product delProduct = productService.deleteProductById(productId);

//		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.status(HttpStatus.OK).body(delProduct);
	}

	// 查詢商品列表
	// 方法一 => 缺點；當傳進來的參數越來越多，會越來越難維護
//	@GetMapping("/products")
//	public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
//			@RequestParam(required = false) String search) {
//		
//		List<Product> productList = productService.getProducts(category, search);
//
//		return ResponseEntity.status(HttpStatus.OK).body(productList);
//	}

	// 方法二 => 多新增一個class來管理參數，較好維護
	// 此方法的分頁功能不會回傳總筆數
//	@GetMapping("/products")
//	public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
//			@RequestParam(required = false) String search, @RequestParam(defaultValue = "created_date") String orderBy,
//			@RequestParam(defaultValue = "desc") String sort, @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
//			@RequestParam(defaultValue = "0") @Min(0) Integer offset) {
//		ProductQueryParams productQueryParams = new ProductQueryParams();
//		productQueryParams.setCategory(category);
//		productQueryParams.setSearch(search);
//		productQueryParams.setOrderBy(orderBy);
//		productQueryParams.setSort(sort);
//		productQueryParams.setLimit(limit);
//		productQueryParams.setOffset(offset);
//
//		List<Product> productList = productService.getProducts(productQueryParams);
//
//		return ResponseEntity.status(HttpStatus.OK).body(productList);
//	}

	// 此方法的分頁功能會回傳總筆數
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
			@RequestParam(required = false) String search, @RequestParam(defaultValue = "created_date") String orderBy,
			@RequestParam(defaultValue = "desc") String sort,
			@RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
			@RequestParam(defaultValue = "0") @Min(0) Integer offset) {
		ProductQueryParams productQueryParams = new ProductQueryParams();
		productQueryParams.setCategory(category);
		productQueryParams.setSearch(search);
		productQueryParams.setOrderBy(orderBy);
		productQueryParams.setSort(sort);
		productQueryParams.setLimit(limit);
		productQueryParams.setOffset(offset);

		List<Product> productList = productService.getProducts(productQueryParams);

		Integer total = productService.countProduct(productQueryParams);

		Page<Product> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setResults(productList);

		return ResponseEntity.status(HttpStatus.OK).body(page);
	}

}
