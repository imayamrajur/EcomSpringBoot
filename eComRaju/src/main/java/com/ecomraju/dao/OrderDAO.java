package com.ecomraju.dao;

import java.util.List;

import com.ecomraju.entity.Customer;
import com.ecomraju.entity.Order;
import com.ecomraju.entity.OrderDetail;
import com.ecomraju.entity.Product;

public interface OrderDAO {
	Order findById(Integer id);

	List<Order> findAll();

	Order create(Order entity);

	void update(Order entity);

	Order delete(Integer id);

	void create(Order order, List<OrderDetail> details);

	List<Order> findByUser(Customer user);

	List<Product> findItemsByUser(Customer user);

}
