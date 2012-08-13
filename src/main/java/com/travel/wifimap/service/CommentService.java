package com.travel.wifimap.service;

import com.travel.wifimap.domain.Comment;

public interface CommentService extends CRUDService<Comment> {

	// public void batchSave(List<? extends Object> objectsToSave);

	public Comment findBySourceId(String source, String sourceId);

}
