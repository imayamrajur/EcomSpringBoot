package com.ecomraju.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ReportDAOImpl implements ReportDAO{
	
	@Autowired
	SessionFactory factory;
	
	@Override
	public List<Object[]> inventory() {
		String findQuery ="SELECT p.category.name,"
				+ " SUM(p.quantity),"
				+ " SUM(p.unitPrice*p.quantity),"
				+ " MIN(p.unitPrice),"
				+ " MAX(p.unitPrice),"
				+ " AVG(p.unitPrice)"
				+ " FROM Product p" 
				+ " GROUP BY p.category.nameVN";
		
		Session session = factory.getCurrentSession();
		TypedQuery<Object[]> query = session.createQuery(findQuery, Object[].class);
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public List<Object[]> revenuByCategory() {
		String findQuery ="SELECT d.product.category.nameVN,"
				+ " SUM(d.quantity),"
				+ " SUM(d.unitPrice*d.quantity),"
				+ " MIN(d.unitPrice),"
				+ " MAX(d.unitPrice),"
				+ " AVG(d.unitPrice)"
				+ " FROM OrderDetail d" 
				+ " GROUP BY d.product.category.nameVN";
		Session session = factory.getCurrentSession();
		TypedQuery<Object[]> query = session.createQuery(findQuery, Object[].class);
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public List<Object[]> revenueByCustomer() {
		String findQuery ="SELECT d.order.customer.fullname,"
				+ " SUM(d.quantity),"
				+ " SUM(d.unitPrice*d.quantity),"
				+ " MIN(d.unitPrice),"
				+ " MAX(d.unitPrice),"
				+ " AVG(d.unitPrice)"
				+ " FROM OrderDetail d" 
				+ " GROUP BY d.order.customer.id";
				//+ " ORDER BY SUM(d.unitPrice*d.quantity) DESC";
		Session session = factory.getCurrentSession();
		TypedQuery<Object[]> query = session.createQuery(findQuery, Object[].class);
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public List<Object[]> revenueByYear() {
		String findQuery ="SELECT YEAR(d.order.orderDate),"
				+ " SUM(d.quantity),"
				+ " SUM(d.unitPrice*d.quantity),"
				+ " MIN(d.unitPrice),"
				+ " MAX(d.unitPrice),"
				+ " AVG(d.unitPrice)"
				+ " FROM OrderDetail d" 
				+ " GROUP BY YEAR(d.order.orderDate)"
				+ "ORDER BY YEAR(d.order.orderDate) DESC";
		Session session = factory.getCurrentSession();
		TypedQuery<Object[]> query = session.createQuery(findQuery, Object[].class);
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public List<Object[]> revenueByQuarter() {
		String findQuery ="SELECT CEILING(MONTH(d.order.orderDate) / 3.0),"
				+ " SUM(d.quantity),"
				+ " SUM(d.unitPrice*d.quantity),"
				+ " MIN(d.unitPrice),"
				+ " MAX(d.unitPrice),"
				+ " AVG(d.unitPrice)"
				+ " FROM OrderDetail d" 
				+ " GROUP BY CEILING(MONTH(d.order.orderDate) / 3.0)"
				+ "ORDER BY CEILING(MONTH(d.order.orderDate) / 3.0)";
		Session session = factory.getCurrentSession();
		TypedQuery<Object[]> query = session.createQuery(findQuery, Object[].class);
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public List<Object[]> revenueByMonth() {
		String findQuery ="SELECT MONTH(d.order.orderDate),"
				+ " SUM(d.quantity),"
				+ " SUM(d.unitPrice*d.quantity),"
				+ " MIN(d.unitPrice),"
				+ " MAX(d.unitPrice),"
				+ " AVG(d.unitPrice)"
				+ " FROM OrderDetail d" 
				+ " GROUP BY MONTH(d.order.orderDate)"
				+ "ORDER BY MONTH(d.order.orderDate) DESC";
		Session session = factory.getCurrentSession();
		TypedQuery<Object[]> query = session.createQuery(findQuery, Object[].class);
		List<Object[]> list = query.getResultList();
		return list;
	}

}
