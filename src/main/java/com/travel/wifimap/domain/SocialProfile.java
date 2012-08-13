package com.travel.wifimap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_SOCIAL_PROFILE")
public class SocialProfile extends BaseEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	// @JoinColumn(name = "ID")
	// @ManyToOne(fetch = FetchType.EAGER)
	// private User user;
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "ACCOUNT_ID")
	private String accountId;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Column(name = "ACCOUNT_LINK")
	private String accountLink;

	@Column(name = "OAUTH_TOKEN")
	private String oauthToken;

	@Column(name = "EXP_DATE")
	private String expDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// public User getUser() {
	// return user;
	// }
	//
	// public void setUser(User user) {
	// this.user = user;
	// }

	public String getAccountName() {
		return accountName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountLink() {
		return accountLink;
	}

	public void setAccountLink(String accountLink) {
		this.accountLink = accountLink;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}