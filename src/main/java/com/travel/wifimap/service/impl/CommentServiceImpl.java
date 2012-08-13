package com.travel.wifimap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.wifimap.dao.CommentDao;
import com.travel.wifimap.domain.Comment;
import com.travel.wifimap.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	// @Override
	// public void batchSave(List<? extends Object> objectsToSave) {
	// commentDao.batchSave(objectsToSave);
	// }

	@Override
	@Transactional
	public void saveOrUpdate(Comment obj) {
		commentDao.saveOrUpdate(obj);
	}

	@Override
	@Transactional
	public void delete(Comment obj) {
		commentDao.delete(obj);
	}

	@Override
	@Transactional
	public Comment findById(String hashKey) {
		return (Comment) commentDao.findById(hashKey);
	}

	@Override
	@Transactional
	public Comment findBySourceId(String source, String sourceId) {
		return commentDao.findBySourceId(source, sourceId);
	}

}
