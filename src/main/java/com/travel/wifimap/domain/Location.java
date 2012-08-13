package com.travel.wifimap.domain;

public class Location {

	private String address;
	private String crossStreet;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String name;
	private Double lat;
	private Double lng;

	public Location() {
		super();
	}

	public String getAddress() {
		return address;
	}

	public String getCrossStreet() {
		return crossStreet;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCountry() {
		return country;
	}

	public String getName() {
		return name;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}
}
