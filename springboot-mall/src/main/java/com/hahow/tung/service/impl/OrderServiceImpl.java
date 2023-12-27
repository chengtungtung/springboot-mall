package com.hahow.tung.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hahow.tung.dao.OrderDao;
import com.hahow.tung.dao.ProductDao;
import com.hahow.tung.dto.BuyItem;
import com.hahow.tung.dto.CreateOrderRequest;
import com.hahow.tung.model.OrderItem;
import com.hahow.tung.model.Product;
import com.hahow.tung.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Transactional // 在修改多個table的情況下，要加此註解，防止途中有錯誤，可以還原原始數據
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();

		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());

			// 計算總價錢
			int amount = buyItem.getQuantity() * product.getPrice();
			totalAmount = totalAmount + amount;

			// 轉換BuyItem to OrderItem
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);
			orderItemList.add(orderItem);
		}

		// 創建訂單
		Integer orderId = orderDao.createOrder(userId, totalAmount);

		orderDao.createOrderItems(orderId, orderItemList);

		return orderId;

	}

}
