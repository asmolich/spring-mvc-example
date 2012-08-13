package com.travel.wifimap.foursquare.search;

public enum VenueParams {
	ll("ll"), near("near"), llAcc("llAcc"), alt("alt"), altAcc("altAcc"), query(
			"query"), limit("limit"), intent("intent"), radius("radius"), sw(
			"sw"), ne("ne"), categoryId("categoryId"), url("url"), providerId(
			"providerId"), linkedId("linkedId"), client_id("client_id"), client_secret(
			"client_secret"), section("section"), basis("basis");
	private String value;

	VenueParams(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
