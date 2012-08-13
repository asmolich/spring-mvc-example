package com.travel.wifimap.dao.impl;

import org.springframework.stereotype.Repository;

import com.travel.wifimap.dao.FeedbackDao;
import com.travel.wifimap.domain.Feedback;

@Repository
public class FeedbackDaoImpl extends CRUDImpl<Feedback> implements FeedbackDao {

	@Override
	public Class<Feedback> getClazz() {
		return Feedback.class;
	}
}
