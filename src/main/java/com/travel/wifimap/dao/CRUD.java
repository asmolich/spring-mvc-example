package com.travel.wifimap.dao;

import java.util.List;

public interface CRUD<T> {

	public void saveOrUpdate(T obj);

	public void delete(T obj);

	public/* T */Object findById(/* Object */String id);

	public/* T */Object load(/* Object */String hashKey);

	public List<T> list();
}
