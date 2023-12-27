package com.hahow.tung.service;

import com.hahow.tung.dto.CreateOrderRequest;
import com.hahow.tung.model.Order;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
	
	Order getOrderById(Integer orderId);
	
}
