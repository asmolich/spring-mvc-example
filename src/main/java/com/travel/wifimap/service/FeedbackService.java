package com.travel.wifimap.service;

import java.util.List;

import com.travel.wifimap.domain.Feedback;

public interface FeedbackService {

	public void send(Feedback feedback);

	public List<Feedback> list();
}
