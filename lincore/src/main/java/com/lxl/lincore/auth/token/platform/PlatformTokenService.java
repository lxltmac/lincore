package com.lxl.lincore.auth.token.platform;

import com.lxl.lincore.auth.token.base.*;
import com.lxl.lincore.auth.token.util.AuthRedisHandler;
import com.lxl.lincore.auth.token.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlatformTokenService extends TokenService{
	
	private TokenProperty config; 

	@Autowired
	public PlatformTokenService(TokenConfig config) {
		this.config = config.getPlatform();
	}
	/**
	 * 创建 管理台token
	 * 
	 * @author linxili
	 * @since 2017年8月1日
	 * @param t
	 */
	public PlatformToken create(BaseToken t){

		PlatformToken token = (PlatformToken)t;

		//拼装accessToken
		String accessToken = JwtHelper.createJWT(
				token.getUserCode(),
				token.getClientId(),
				config.getName(),
				config.getExpiresSecond() * 1000,
				config.getBase64Secret()
		);
		//token信息
		token.setUser(token.getUserCode());
		token.setAccessToken(accessToken);
		token.setExpires(config.getExpiresSecond());
		token.setTokenType(TokenType.platform.name());
		token.setFastExpires(config.getFastExpires());
		token.setTokenLimit(config.getTokenLimit());

		//只允许存在一个有效的Token
		if(config.getTokenLimit() == 1){
			//将之前的Token过期时间设置过期
			AuthRedisHandler.setOldAccessTokenFastExpires(token.getUser(), token.getFastExpires());
			//将新的Token设置为CurrentToken
			AuthRedisHandler.refreshCurrentUserToken(token.getUser(), accessToken);
		}


		//保存token(加解密钥(EncodingAesKey不能在接口传递))
		AuthRedisHandler.saveOrRefreshAccessToken(token);

		//登陆信息
		PlatformToken result = new PlatformToken();

		//Token信息
		result.setUser(token.getUserCode());
		result.setAccessToken(accessToken);
		result.setExpires(config.getExpiresSecond());
		result.setTokenType(TokenType.platform.name());


		//保存token
		AuthRedisHandler.saveOrRefreshAccessToken(result);

		return result;
	}

}
