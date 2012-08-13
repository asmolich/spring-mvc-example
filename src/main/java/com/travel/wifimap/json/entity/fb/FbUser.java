package com.travel.wifimap.json.entity.fb;

public class FbUser extends FbObject {

	private String name;
	private String first_name;
	private String last_name;
	private String username;
	private SimpleFbObject location;
	private SimpleFbObject hometown;
	private String gender;
	private String email;
	private String link;
	private String birthday;
	private String picture;

	public FbUser() {
		super();
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SimpleFbObject getLocation() {
		return location;
	}

	public void setLocation(SimpleFbObject location) {
		this.location = location;
	}

	public SimpleFbObject getHometown() {
		return hometown;
	}

	public void setHometown(SimpleFbObject hometown) {
		this.hometown = hometown;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
