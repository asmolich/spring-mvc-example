package com.travel.wifimap;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

public class OAuthServiceProvider {

	private OAuthServiceConfig config;

	public OAuthServiceProvider() {
	}

	public OAuthServiceProvider(OAuthServiceConfig config) {
		this.config = config;
	}

	public OAuthService getService() {

		ServiceBuilder serviceBuilder = new ServiceBuilder();
		serviceBuilder.provider(config.getApiClass())
				.apiKey(config.getApiKey()).apiSecret(config.getApiSecret())
				.callback(config.getCallback());
		if (config.getScope() != null) {
			serviceBuilder.scope(config.getScope());
		}
		return serviceBuilder.build();
	}

}
