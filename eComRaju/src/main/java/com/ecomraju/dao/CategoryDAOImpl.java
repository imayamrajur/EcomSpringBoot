package com.ecomraju.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecomraju.entity.Category;

@Transactional
@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	SessionFactory factory;

	@Override
	public Category findById(Integer id) {
		Session session = factory.getCurrentSession();
		Category entity = session.find(Category.class, id);
		return entity;
	}

	@Override
	public List<Category> findAll() {
		String findQuery = "FROM Category";
		Session session = factory.getCurrentSession();
		TypedQuery<Category> query = session.createQuery(findQuery, Category.class);
		List<Category> list = query.getResultList();
		return list;
	}

	@Override
	public Category create(Category entity) {
		Session session = factory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	@Override
	public void update(Category entity) {
		Session session = factory.getCurrentSession();
		session.update(entity);

	}

	@Override
	public Category delete(Integer id) {
		Session session = factory.getCurrentSession();
		Category entity = session.find(Category.class, id);
		session.delete(entity);
		return entity;
	}

	@Override
	public List<Category> getRandoms() {
		String findQuery = "FROM Category";
		Session session = factory.getCurrentSession();
		TypedQuery<Category> query = session.createQuery(findQuery, Category.class);
		List<Category> list = query.getResultList();
		/* Collections.shuffle(list);
		list = list.subList(0, 2);
		list.forEach(c->{
			List<Product> prods = c.getProducts();
			Collections.shuffle(prods);
			c.setProducts(prods.subList(0, 2));
		});*/
		return list;
	}

}
