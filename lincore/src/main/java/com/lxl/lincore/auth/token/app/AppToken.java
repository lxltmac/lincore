package com.lxl.lincore.auth.token.app;

import com.lxl.lincore.auth.token.base.BaseToken;

/**
 * 
 * 移动端登陆信息
 * 
 * @author linxili
 *
 * @since 2017年6月26日
 *
 * @version 1.0
 */
public class AppToken extends BaseToken {
	
	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 用户Id
	 */
	private String userId;
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * 用户Id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 用户Id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
