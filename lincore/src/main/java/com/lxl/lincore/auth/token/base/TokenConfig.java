package com.lxl.lincore.auth.token.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="jwt")
@PropertySource(value="classpath:config/jwt.properties")
public class TokenConfig {

	
	private TokenProperty api;
	
	private TokenProperty platform;
	
	private TokenProperty app;
	
	private TokenProperty wxplatform;

	public TokenProperty getApi() {
		return api;
	}

	public void setApi(TokenProperty api) {
		this.api = api;
	}

	public TokenProperty getPlatform() {
		return platform;
	}

	public void setPlatform(TokenProperty platform) {
		this.platform = platform;
	}

	public TokenProperty getApp() {
		return app;
	}

	public void setApp(TokenProperty app) {
		this.app = app;
	}

	public TokenProperty getWxplatform() {
		return wxplatform;
	}

	public void setWxplatform(TokenProperty wxplatform) {
		this.wxplatform = wxplatform;
	}
	
}

	
