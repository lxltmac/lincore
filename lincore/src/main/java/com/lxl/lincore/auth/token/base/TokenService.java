package com.lxl.lincore.auth.token.base;



public abstract class TokenService {
	
	/**
	 * 根据请求参数创建Token
	 * 
	 * @author linxili
	 * @since 2017年8月24日
	 * @param token
	 * @return
	 */
	public abstract BaseToken create(BaseToken token);
}
