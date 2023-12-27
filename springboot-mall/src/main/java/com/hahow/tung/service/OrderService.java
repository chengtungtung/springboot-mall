package com.hahow.tung.service;

import com.hahow.tung.dto.CreateOrderRequest;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
	
}
