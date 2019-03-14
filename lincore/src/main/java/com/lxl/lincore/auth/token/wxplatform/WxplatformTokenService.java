package com.lxl.lincore.auth.token.wxplatform;

import com.lxl.lincore.auth.token.base.*;
import com.lxl.lincore.auth.token.util.AuthRedisHandler;
import com.lxl.lincore.auth.token.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WxplatformTokenService extends TokenService{
	
	private TokenProperty config;

	@Autowired
	public WxplatformTokenService(TokenConfig config) {
		this.config = config.getWxplatform();
	}
	
	/**
	 * 创建管理台登陆token
	 * 
	 * @author linxili
	 * @since 2017年8月1日
	 * @param t
	 * @return
	 */
	public WxplatformToken create(BaseToken t){
		
		WxplatformToken token = (WxplatformToken)t;
        
        //拼装accessToken  
        String accessToken = JwtHelper.createJWT(
        		token.getUserCode(), 
        		token.getClientId(), 
        		config.getName(),  
        		config.getExpiresSecond() * 1000, 
        		config.getBase64Secret()
                );  
       
        
        //登陆信息
        WxplatformToken result = new WxplatformToken();
        
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
