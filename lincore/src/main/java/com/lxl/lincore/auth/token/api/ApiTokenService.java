package com.lxl.lincore.auth.token.api;

import com.lxl.lincore.auth.token.base.*;
import com.lxl.lincore.auth.token.util.AuthRedisHandler;
import com.lxl.lincore.auth.token.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiTokenService extends TokenService{
	
	private TokenProperty config; 

	@Autowired
	public ApiTokenService(TokenConfig config) {
		this.config = config.getApi();
	}

	/**
	 * 创建 APP客户端token
	 * 
	 * @author linxili
	 * @since 2017年8月1日
	 * @param token
	 */
	public ApiToken create(BaseToken t){
		
		ApiToken token = (ApiToken)t;
		
		 //拼装accessToken  
        String accessToken = JwtHelper.createJWT(
        		token.getUserCode(),
        		"api", 
        		config.getName(),  
        		config.getExpiresSecond() * 1000, 
        		config.getBase64Secret()
                ); 
        
        //token信息
        token.setUser(token.getUserCode());
        token.setAccessToken(accessToken);
        token.setExpires(config.getExpiresSecond());
        token.setTokenType(TokenType.api.name());
        token.setRefleshExpires(config.isRefleshExpires());
        token.setFastExpires(config.getFastExpires());
        token.setTokenLimit(config.getTokenLimit());
        
        //只允许存在一个有效的Token
        if(config.getTokenLimit() == 1){
        	//将之前的Token过期时间设置快速过期
        	if(token.getFastExpires() == 0){
        		AuthRedisHandler.deleteAcessTokenByUserCode(token.getUser());
        	}else{
        		AuthRedisHandler.setOldAccessTokenFastExpires(token.getUser(), token.getFastExpires());
        	}
        	//将新的Token设置为CurrentToken
        	AuthRedisHandler.refreshCurrentUserToken(token.getUser(), accessToken);
        }
        
        
        //保存token(加解密钥(EncodingAesKey不能在接口传递))
        AuthRedisHandler.saveOrRefreshAccessToken(token);
        
        //设置返回的数据
        ApiToken result = new ApiToken();
        
        result.setAccessToken(token.getAccessToken());
        result.setExpiresTime(token.getExpiresTime());
        result.setExpires(token.getExpires());
        
		return result;
	}

}
