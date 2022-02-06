package com.ecomraju.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecomraju.entity.Customer;
import com.ecomraju.entity.Order;
import com.ecomraju.entity.OrderDetail;
import com.ecomraju.entity.Product;

@Transactional
@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	SessionFactory factory;

	@Override
	public Order findById(Integer id) {
		Session session = factory.getCurrentSession();
		Order entity = session.find(Order.class, id);
		return entity;
	}

	@Override
	public List<Order> findAll() {
		String findQuery = "FROM Order";
		Session session = factory.getCurrentSession();
		TypedQuery<Order> query = session.createQuery(findQuery, Order.class);
		List<Order> list = query.getResultList();
		return list;
	}

	@Override
	public Order create(Order entity) {
		Session session = factory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	@Override
	public void update(Order entity) {
		Session session = factory.getCurrentSession();
		session.update(entity);

	}

	@Override
	public Order delete(Integer id) {
		Session session = factory.getCurrentSession();
		Order entity = session.find(Order.class, id);
		session.delete(entity);
		return entity;
	}

	@Override
	public void create(Order order, List<OrderDetail> details) {
		Session session = factory.getCurrentSession();
		session.save(order);
		for(OrderDetail detail: details) {
			session.save(detail);
		}
	}

	@Override
	public List<Order> findByUser(Customer user) {
		String findQuery = "FROM Order o WHERE o.customer.id=:uid ORDER BY o.orderDate DESC";
		Session session = factory.getCurrentSession();
		TypedQuery<Order> query = session.createQuery(findQuery, Order.class);
		query.setParameter("uid", user.getId());
		List<Order> list = query.getResultList();
		return list;
	}

	@Override
	public List<Product> findItemsByUser(Customer user) {
		String findQuery = "SELECT DISTINCT d.product"
				+" FROM OrderDetail d"
				+" WHERE d.order.customer.id=:uid";
		Session session = factory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery(findQuery, Product.class);
		query.setParameter("uid", user.getId());
		List<Product> list = query.getResultList();
		return list;
	}

}
