package com.hahow.tung.service;

import java.util.List;

import com.hahow.tung.dto.CreateOrderRequest;
import com.hahow.tung.dto.OrderQueryParams;
import com.hahow.tung.model.Order;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
	
	Order getOrderById(Integer orderId);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	Integer countOrder(OrderQueryParams orderQueryParams);
}
