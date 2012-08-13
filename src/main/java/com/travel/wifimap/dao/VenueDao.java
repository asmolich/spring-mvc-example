package com.travel.wifimap.dao;

import java.util.List;

import com.travel.wifimap.domain.Comment;
import com.travel.wifimap.domain.Venue;

public interface VenueDao extends CRUD<Venue> {

	// public void batchSave(List<? extends Object> objectsToSave);

	public Venue findBySourceId(String source, String sourceId);

	public List<Comment> getVenueComments(String venueId);

	public List<Venue> search(Double lat, Double lng, Double radius,
			Integer limit);

}
