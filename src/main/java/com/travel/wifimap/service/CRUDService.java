package com.travel.wifimap.service;

public interface CRUDService<T> {

	public void saveOrUpdate(T obj);

	public void delete(T obj);

	public T findById(/* Object */String hashKey);
}
