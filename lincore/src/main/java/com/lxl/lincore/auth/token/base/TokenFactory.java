package com.lxl.lincore.auth.token.base;

import com.lxl.common.helper.SpringFactory;

/**
 * 
 * AccessToken 工厂实例
 *
 * @author linxili
 * @date 2017年12月14日 下午4:54:16 
 *
 */
public class TokenFactory {

	public static TokenService getService(String accessType){
		
		String service = accessType +"TokenService";
		
		return SpringFactory.getBean(service);
	}
}
