package com.lxl.lincore.auth.token.app;

import com.lxl.lincore.auth.token.base.*;
import com.lxl.lincore.auth.token.util.AuthRedisHandler;
import com.lxl.lincore.auth.token.util.JwtHelper;
import com.lxl.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppTokenService extends TokenService{

	private TokenProperty config; 

	@Autowired
	public AppTokenService(TokenConfig config) {
		this.config = config.getApp();
	}

	/**
	 * 创建 APP客户端token
	 * 
	 * @author linxili
	 * @since 2017年8月1日
	 * @param token
	 */
	public AppToken create(BaseToken t){

		AppToken token = (AppToken)t;

		//拼装accessToken  
		String accessToken = JwtHelper.createJWT(
				token.getPhone(),  
				token.getClientId(), 
				config.getName(),  
				config.getExpiresSecond() * 1000, 
				config.getBase64Secret()
				);  

		AppToken result = new AppToken();
		//token信息
		result.setUser(token.getPhone());
		result.setAccessToken(accessToken);
		result.setUserId(token.getUserId());
		result.setExpires(config.getExpiresSecond());
		result.setTokenType(TokenType.app.name());
		// 删除该userID原先的token
		String tokenByField = AuthRedisHandler.getCurrentUserTokenByField(token.getUserId());
		if (StringUtils.isNotEmpty(tokenByField)) {
			AuthRedisHandler.deleteAcessTokenByUserCode(tokenByField);
		}
		//保存token
		AuthRedisHandler.saveOrRefreshAccessToken(result);
		// 将最新的userID和token绑定到redis(该redis,通过userID查找token)
		AuthRedisHandler.refreshCurrentUserToken(token.getUserId(), accessToken);

		return result;
	}

}
