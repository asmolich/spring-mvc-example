package com.travel.wifimap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.wifimap.dao.CategoryDao;
import com.travel.wifimap.domain.Category;
import com.travel.wifimap.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	// @Override
	// public void batchSave(List<? extends Object> objectsToSave) {
	// categoryDao.batchSave(objectsToSave);
	// }

	@Override
	@Transactional
	public void saveOrUpdate(Category obj) {
		categoryDao.saveOrUpdate(obj);
	}

	@Override
	@Transactional
	public void delete(Category obj) {
		categoryDao.delete(obj);
	}

	@Override
	@Transactional
	public Category findById(String hashKey) {
		return (Category) categoryDao.findById(hashKey);
	}

	@Override
	@Transactional
	public List<Category> list() {
		return categoryDao.list();
	}

}
