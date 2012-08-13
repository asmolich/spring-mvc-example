package com.travel.wifimap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.wifimap.dao.CheckinDao;
import com.travel.wifimap.domain.Checkin;
import com.travel.wifimap.service.CheckinService;

@Service
public class CheckinServiceImpl implements CheckinService {

	@Autowired
	private CheckinDao checkinDao;

	@Override
	@Transactional
	public void saveOrUpdate(Checkin obj) {
		checkinDao.saveOrUpdate(obj);
	}

	@Override
	@Transactional
	public void delete(Checkin obj) {
		checkinDao.delete(obj);
	}

	@Override
	@Transactional
	public Checkin findById(String hashKey) {
		return (Checkin) checkinDao.findById(hashKey);
	}

	// @Override
	// public List<Checkin> findCheckinsOfUser(String userId) {
	// return checkinDao.findCheckinsOfUser(userId);
	// }
	//
	// @Override
	// public List<Checkin> findCheckinsInVenue(String venueId) {
	// return checkinDao.findCheckinsInVenue(venueId);
	// }
}
