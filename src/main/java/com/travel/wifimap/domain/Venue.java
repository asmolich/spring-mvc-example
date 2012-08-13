package com.travel.wifimap.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_VENUE")
// @DynamoDBTable(tableName = "T_VENUE")
public class Venue extends BaseEntity {

	private String id;
	private String name;
	private String source;
	private String sourceId;
	private String address;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String photoUrl;
	private Double latitude;
	private Double longitude;
	private String ssid;
	private String password;

	// private List<String> categoryIds;

	private List<Category> categories;

	private List<Comment> comments;

	public Venue() {
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

	@Column(name = "NAME")
	// @DynamoDBAttribute(attributeName = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SOURCE")
	// @DynamoDBAttribute(attributeName = "SOURCE")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "SOURCE_ID")
	// @DynamoDBAttribute(attributeName = "SOURCE_ID")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "ADDRESS")
	// @DynamoDBAttribute(attributeName = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CITY")
	// @DynamoDBAttribute(attributeName = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE")
	// @DynamoDBAttribute(attributeName = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "POSTAL_CODE")
	// @DynamoDBAttribute(attributeName = "POSTAL_CODE")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "COUNTRY")
	// @DynamoDBAttribute(attributeName = "COUNTRY")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "LATITUDE")
	// @DynamoDBAttribute(attributeName = "LATITUDE")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "LONGITUDE")
	// @DynamoDBAttribute(attributeName = "LONGITUDE")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	// @DynamoDBAttribute(attributeName = "CATEGORIES")
	// public List<String> getCategoryIds() {
	// return categoryIds;
	// }
	//
	// public void setCategoryIds(List<String> categoryIds) {
	// this.categoryIds = categoryIds;
	// }

	@Column(name = "PHOTO_URL")
	// @DynamoDBAttribute(attributeName = "PHOTO_URL")
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "SSID")
	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "T_VENUE_CATEGORY", joinColumns = { @JoinColumn(name = "VENUE_ID") }, inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID") })
	@Fetch(FetchMode.SUBSELECT)
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@OneToMany(mappedBy = "venue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// @Fetch(FetchMode.SUBSELECT)
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Venue [id=" + id + ", name=" + name + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", latitude="
				+ latitude + ", longitude=" + longitude + ", comments.size()="
				+ comments.size() + "]";
	}
}
