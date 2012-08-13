package com.travel.wifimap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.wifimap.dao.VenueDao;
import com.travel.wifimap.domain.Comment;
import com.travel.wifimap.domain.Venue;
import com.travel.wifimap.service.VenueService;

@Service
public class VenueServiceImpl implements VenueService {

	@Autowired
	private VenueDao venueDao;

	@Override
	@Transactional
	public void saveOrUpdate(Venue obj) {
		venueDao.saveOrUpdate(obj);
	}

	@Override
	@Transactional
	public void delete(Venue obj) {
		venueDao.delete(obj);
	}

	@Override
	@Transactional
	public Venue findById(String hashKey) {
		return (Venue) venueDao.findById(hashKey);
	}

	// @Override
	// public void batchSave(List<? extends Object> objectsToSave) {
	// venueDao.batchSave(objectsToSave);
	// }

	@Override
	@Transactional
	public Venue findBySourceId(String source, String sourceId) {
		return venueDao.findBySourceId(source, sourceId);
	}

	@Override
	@Transactional
	public List<Comment> getVenueComments(String venueId) {
		return venueDao.getVenueComments(venueId);
	}

	@Override
	@Transactional
	public List<Venue> search(Double lat, Double lng, Double radius,
			Integer limit) {

		return venueDao.search(lat, lng, radius, limit);
	}
}
