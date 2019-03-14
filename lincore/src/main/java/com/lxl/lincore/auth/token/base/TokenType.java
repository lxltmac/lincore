package com.lxl.lincore.auth.token.base;

import com.lxl.lincore.auth.token.api.ApiToken;
import com.lxl.lincore.auth.token.app.AppToken;
import com.lxl.lincore.auth.token.platform.PlatformToken;
import com.lxl.lincore.auth.token.wxplatform.WxplatformToken;


public enum TokenType {

	app(AppToken.class),
	
	platform(PlatformToken.class),
	
	wxplatform(WxplatformToken.class),
	
	api(ApiToken.class),
	;
	
	private Class<? extends BaseToken> tokenClass;

	public Class<? extends BaseToken> getTokenClass() {
		return tokenClass;
	}
	
	private TokenType(Class<? extends BaseToken> tokenClass) {
		this.tokenClass = tokenClass;
	}
	
	public static Class<? extends BaseToken> getTokenClass(String type) {
		return TokenType.valueOf(type).tokenClass;
	}
	
}
