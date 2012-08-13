package com.travel.wifimap.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.wifimap.dao.CRUD;

public abstract class CRUDImpl<T> implements CRUD<T> {

	@Autowired
	// private DynamoDBMapper dynamoDBMapper;
	private SessionFactory sessionFactory;

	// public DynamoDBMapper getDynamoDBMapper() {
	// return dynamoDBMapper;
	// }

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void saveOrUpdate(T obj) {
		// dynamoDBMapper.save(obj);
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void delete(T obj) {
		// dynamoDBMapper.delete(obj);
		sessionFactory.getCurrentSession().delete(obj);
		sessionFactory.getCurrentSession().flush();
	}

	public abstract Class<T> getClazz();

	@Override
	public/* T */Object findById(String id) {
		String queryString = "from " + getClazz().getCanonicalName()
				+ " where id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(
				queryString);
		query.setString("id", id);
		List list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<T> list() {
		// DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		// return dynamoDBMapper.scan(getClazz(), scanExpression);
		String queryString = "from " + getClazz().getCanonicalName();
		Query query = sessionFactory.getCurrentSession().createQuery(
				queryString);
		return query.list();
	}

	@Override
	public/* T */Object load(String hashKey) {
		// return dynamoDBMapper.load(getClazz(), hashKey);
		return sessionFactory.getCurrentSession().load(getClazz(), hashKey);
	}
}
