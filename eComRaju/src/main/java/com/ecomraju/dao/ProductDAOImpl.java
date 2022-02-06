package com.ecomraju.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecomraju.entity.Product;

@Transactional
@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	SessionFactory factory;

	@Override
	public Product findById(Integer id) {
		Session session = factory.getCurrentSession();
		Product entity = session.find(Product.class, id);
		return entity;
	}

	@Override
	public List<Product> findAll() {
		String findQuery = "FROM Product";
		Session session = factory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery(findQuery, Product.class);
		List<Product> list = query.getResultList();
		return list;
	}

	@Override
	public Product create(Product entity) {
		Session session = factory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	@Override
	public void update(Product entity) {
		Session session = factory.getCurrentSession();
		session.update(entity);

	}

	@Override
	public Product delete(Integer id) {
		Session session = factory.getCurrentSession();
		Product entity = session.find(Product.class, id);
		session.delete(entity);
		return entity;
	}

	@Override
	public List<Product> findByCategoryId(Integer categoryId) {
		String findQuery = "FROM Product p WHERE p.category.id=: cid";
		Session session = factory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery(findQuery, Product.class);
		query.setParameter("cid", categoryId);
		List<Product> list = query.getResultList();
		return list;
	}

	@Override
	public List<Product> findByKeywords(String keywords) {
		String findQuery = "FROM Product p " + " WHERE p.name LIKE :kw OR p.category.name LIKE :kw OR p.category.nameVN LIKE :kw";
		//String findQuery = "FROM Product p " + " WHERE p.name LIKE :kw";
		Session session = factory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery(findQuery, Product.class);
		query.setParameter("kw", "%"+ keywords+"%");
		List<Product> list = query.getResultList();
		return list;
	}

	@Override
	public List<Product> findByIds(String ids) {
		String findQuery = "FROM Product p WHERE p.id IN ("+ids+")";
		Session session = factory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery(findQuery, Product.class);
		List<Product> list = query.getResultList();
		return list;
	}

	@Override
	public List<Product> findBySpecial(Integer id) {
		
		Session session = factory.getCurrentSession();
		String findQuery ="FROM Product p";
		TypedQuery<Product> query = session.createQuery(findQuery, Product.class);
		
		switch(id) {
		case 0:
			findQuery = "FROM Product p ORDER BY p.productDate DESC"; 
			break;
		case 1:
			findQuery = "FROM Product p ORDER BY size(p.orderDetails) DESC"; 
			break;
		case 2:
			findQuery = "FROM Product p ORDER BY p.viewCount DESC"; 
			break;
		case 3:
			findQuery = "FROM Product p ORDER BY p.discount DESC"; 
			break;
		case 4:
			findQuery = "FROM Product p WHERE p.special = true"; 
			break;
		}
		
		
		query = session.createQuery(findQuery, Product.class);
		query.setMaxResults(12);		
		List<Product> list = query.getResultList();
		return list;
	}

}
