package com.hahow.tung.dao;

import java.util.List;

import com.hahow.tung.model.Order;
import com.hahow.tung.model.OrderItem;

public interface OrderDao {

	Integer createOrder(Integer userId, Integer totalAmount);

	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

	Order getOrderById(Integer orderId);

	List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
