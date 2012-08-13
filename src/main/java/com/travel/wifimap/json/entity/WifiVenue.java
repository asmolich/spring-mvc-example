package com.travel.wifimap.json.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteTip;
import fi.foyt.foursquare.api.entities.Location;
import fi.foyt.foursquare.api.entities.Recommendation;

public final class WifiVenue {

	private String id;
	private String name;
	private Location loc;
	private List<Category> types;
	private List<CompleteTip> comments;

	public WifiVenue(Recommendation rec) {
		super();
		CompactVenue venue = rec.getVenue();
		id = venue.getId();
		name = venue.getName();
		loc = venue.getLocation();
		types = Arrays.asList(venue.getCategories());
		comments = Arrays.asList(rec.getTips());
	}

	public WifiVenue(CompleteTip tip) {
		CompactVenue venue = tip.getVenue();
		id = venue.getId();
		name = venue.getName();
		loc = venue.getLocation();
		types = Arrays.asList(venue.getCategories());
		comments = new ArrayList<CompleteTip>();
		comments.add(tip);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Location getLoc() {
		return loc;
	}

	public List<Category> getTypes() {
		return types;
	}

	public List<CompleteTip> getComments() {
		return comments;
	}

	public WifiVenue merge(WifiVenue venue) {
		if (this.id.equals(venue.getId())) {

			List<CompleteTip> result = new ArrayList<CompleteTip>(this.comments);
			for (CompleteTip newComment : venue.getComments()) {
				boolean exists = false;
				for (CompleteTip comment : this.comments) {
					if (comment.getId().equals(newComment.getId())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					result.add(newComment);
				}
			}

			this.comments = result;
		}
		return this;
	}
}
