package com.lxl.lincore.auth.token.base;

import java.util.Date;

/**
 * Token 信息
 * 
 * @author linxili
 *
 * @since 2017年6月26日
 *
 * @version 1.0
 */
public class BaseToken {
	
	/**
	 * 私有构建函数,不参直接new BaseToken(),
	 * 只能通过创建子类
	 */
	protected BaseToken(){
		
	}

	/**
	 * 用户
	 */
	private String user;

	/**
	 * 客户端标识
	 */
	private String clientId;

	/**
	 * 访问token
	 */
	private String accessToken;

	/**
	 * token类型
	 */
	private String tokenType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 过期时间(单位秒)
	 */
	private int expires;

	/**
	 * 过期时间(每次请求每刷新过期时间)
	 */
	private Date expiresTime;
	
	/**
	 * 每次请求是否刷新Token的过期时间
	 */
	private Boolean refleshExpires;
	
	/**
	 * 同一个用户可获取的Token数(默认为不控制)
	 */
	private Integer tokenLimit;
	
	/**
	 * 快速过期时间(单位秒)
	 * 
	 * 当tokenLimit = 1 时,旧的Token快速失效的时间
	 * 
	 */
	private Integer fastExpires;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public int getExpires() {
		return expires;
	}

	public void setExpires(int expires) {
		this.expires = expires;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	public Boolean getRefleshExpires() {
		return refleshExpires;
	}

	public void setRefleshExpires(Boolean refleshExpires) {
		this.refleshExpires = refleshExpires;
	}

	public Integer getTokenLimit() {
		return tokenLimit;
	}

	public void setTokenLimit(Integer tokenLimit) {
		this.tokenLimit = tokenLimit;
	}

	public Integer getFastExpires() {
		return fastExpires;
	}

	public void setFastExpires(Integer fastExpires) {
		this.fastExpires = fastExpires;
	}

}
