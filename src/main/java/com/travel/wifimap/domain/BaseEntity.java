package com.travel.wifimap.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	private Timestamp createdAt;

	public BaseEntity() {
		super();
	}

	@Column(name = "CREATED_AT")
	// @DynamoDBAttribute(attributeName = "CREATED_AT")
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
