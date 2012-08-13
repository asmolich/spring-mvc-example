package com.travel.wifimap.service;

import java.util.List;

import com.travel.wifimap.domain.Category;

public interface CategoryService extends CRUDService<Category> {

	// public void batchSave(List<? extends Object> objectsToSave);

	public List<Category> list();
}
