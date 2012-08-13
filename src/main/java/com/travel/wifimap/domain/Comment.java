package com.travel.wifimap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COMMENT")
// @DynamoDBTable(tableName = "T_COMMENT")
public class Comment {

	private String id;
	private String text;
	private String source;
	private String sourceId;
	private Long createdAt;
	private String userId;
	// private String venueId;

	private Venue venue;

	public Comment() {
		super();
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	// @DynamoDBHashKey(attributeName = "ID")
	// @DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TEXT")
	// @DynamoDBHashKey(attributeName = "TEXT")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "CREATED_AT")
	// @DynamoDBHashKey(attributeName = "CREATED_AT")
	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "USER_ID")
	// @DynamoDBHashKey(attributeName = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "SOURCE")
	// @DynamoDBHashKey(attributeName = "SOURCE")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "SOURCE_ID")
	// @DynamoDBHashKey(attributeName = "SOURCE_ID")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@ManyToOne
	@JoinColumn(name = "VENUE_ID")
	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	// @Column(name = "VENUE_ID")
	// // @DynamoDBHashKey(attributeName = "VENUE_ID")
	// public String getVenueId() {
	// return venueId;
	// }
	//
	// public void setVenueId(String venueId) {
	// this.venueId = venueId;
	// }
}
