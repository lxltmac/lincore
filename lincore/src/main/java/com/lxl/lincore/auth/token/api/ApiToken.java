package com.lxl.lincore.auth.token.api;

import com.lxl.lincore.auth.token.base.BaseToken;

/**
 * 外部接口调用 Token 
 *
 * @author linxili
 * @date 2017年12月26日 上午10:23:36 
 *
 */
public class ApiToken extends BaseToken {

	/**
	 * 用户帐号 
	 */
	private String userCode;
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 密钥
	 */
	private String encodingAesKey;
	

	public String getEncodingAesKey() {
		return encodingAesKey;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
