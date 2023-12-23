package com.hahow.tung.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hahow.tung.dto.ProductRequest;
import com.hahow.tung.model.Product;
import com.hahow.tung.service.ProductService;

@RestController
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

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

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
		log.info("進入ProductController的createProduct方法");

		Integer productId = productService.createProduct(productRequest);

		Product product = productService.getProductById(productId);

		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
			@RequestBody @Valid ProductRequest productRequest) {
		log.info("進入ProductController的updateProduct方法 => 修改的productId值為 " + productId);
		Product product = productService.getProductById(productId);

		if (product != null) {
			productService.updateProduct(productId, productRequest);
			return ResponseEntity.status(HttpStatus.OK).body(product);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
		log.info("進入ProductController的deleteProduct方法 => 刪除的productId值為 " + productId);

		Product delProduct = productService.deleteProductById(productId);

//		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.status(HttpStatus.OK).body(delProduct);
	}

}
