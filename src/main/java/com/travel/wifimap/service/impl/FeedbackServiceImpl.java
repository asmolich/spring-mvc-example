package com.travel.wifimap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.wifimap.dao.FeedbackDao;
import com.travel.wifimap.domain.Feedback;
import com.travel.wifimap.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackDao feedbackDao;

	@Override
	@Transactional
	public void send(Feedback feedback) {
		feedbackDao.saveOrUpdate(feedback);
	}

	@Override
	@Transactional
	public List<Feedback> list() {
		return feedbackDao.list();
	}
}
