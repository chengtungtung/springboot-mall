package com.hahow.tung.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.hahow.tung.dao.OrderDao;
import com.hahow.tung.dao.ProductDao;
import com.hahow.tung.dao.UserDao;
import com.hahow.tung.dto.BuyItem;
import com.hahow.tung.dto.CreateOrderRequest;
import com.hahow.tung.model.Order;
import com.hahow.tung.model.OrderItem;
import com.hahow.tung.model.Product;
import com.hahow.tung.model.User;
import com.hahow.tung.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	@Transactional // 在修改多個table的情況下，要加此註解，防止途中有錯誤，可以還原原始數據
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		// 檢查 user 是否存在
		User user = userDao.getUserById(userId);

		if (user == null) {
			log.warn("該 userId 不存在 => {}", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();

		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());

			// 檢查 product 是否存在、庫存是否足夠
			if (product == null) {
				log.warn("商品不存在 => {}", buyItem.getProductId());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

				// 檢查庫存是否足夠
			} else if (buyItem.getQuantity() > product.getStock()) {
				log.warn("商品 {} 庫存數量不夠，庫存數量 => {}，購買數量 => {}", buyItem.getProductId(), product.getStock(),
						buyItem.getQuantity());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}

			// 扣除商品庫存
			productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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

	@Override
	public Order getOrderById(Integer orderId) {
		Order order = orderDao.getOrderById(orderId);

		List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

		order.setOrderItemList(orderItemList);

		return order;
	}

}
