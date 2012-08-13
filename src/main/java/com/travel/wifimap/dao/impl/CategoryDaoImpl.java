package com.travel.wifimap.dao.impl;

import org.springframework.stereotype.Repository;

import com.travel.wifimap.dao.CategoryDao;
import com.travel.wifimap.domain.Category;

@Repository
public class CategoryDaoImpl extends CRUDImpl<Category> implements CategoryDao {

	@Override
	public Class<Category> getClazz() {
		return Category.class;
	}

	// public void batchSave(List<? extends Object> objectsToSave) {
	// getDynamoDBMapper().batchSave(objectsToSave);
	// }

}