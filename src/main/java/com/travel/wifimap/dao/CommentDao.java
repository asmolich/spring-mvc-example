package com.travel.wifimap.dao;

import com.travel.wifimap.domain.Comment;

public interface CommentDao extends CRUD<Comment> {

	// public void batchSave(List<? extends Object> objectsToSave);

	public Comment findBySourceId(String source, String sourceId);

}
