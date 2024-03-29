package com.travel.wifimap;

import org.scribe.builder.api.Api;

public class OAuthServiceConfig {

	private String apiKey = null;
	private String apiSecret = null;
	private String callback = null;
	private String scope = null;
	private Class<? extends Api> apiClass;

	public OAuthServiceConfig() {
	}

	public OAuthServiceConfig(String apiKey, String apiSecret, String callback,
			Class<? extends Api> apiClass) {
		this(apiKey, apiSecret, callback, null, apiClass);
	}

	public OAuthServiceConfig(String apiKey, String apiSecret, String callback,
			String scope, Class<? extends Api> apiClass) {
		super();
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.callback = callback;
		this.scope = scope;
		this.apiClass = apiClass;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey
	 *            the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the apiSecret
	 */
	public String getApiSecret() {
		return apiSecret;
	}

	/**
	 * @param apiSecret
	 *            the apiSecret to set
	 */
	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * @return the apiClass
	 */
	public Class<? extends Api> getApiClass() {
		return apiClass;
	}

	/**
	 * @param apiClass
	 *            the apiClass to set
	 */
	public void setApiClass(Class<? extends Api> apiClass) {
		this.apiClass = apiClass;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OAuthServiceConfig [apiKey=" + apiKey + ", apiSecret="
				+ apiSecret + ", callback=" + callback + ", apiClass="
				+ apiClass + "]";
	}

}
