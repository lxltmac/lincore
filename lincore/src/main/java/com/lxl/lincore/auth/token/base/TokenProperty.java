package com.lxl.lincore.auth.token.base;

public class TokenProperty {

	/**
	 * 客户端标识
	 */
	private String clientId;  
	
	/**
	 * 创建Token的密钥
	 */
    private String base64Secret; 
    
    /**
     * 创建Token的类型
     */
    private String name;  
    
    /**
     * Token过期时间
     */
    private int expiresSecond;
    
    /**
	 * 每次请求是否刷新Token的过期时间,默认为true
	 */
	private boolean refleshExpires = true;
	
	/**
	 * 同一个用户可获取的Token数(默认为不控制)
	 */
	private int tokenLimit = -1;
	
	/**
	 * 快速过期时间(单位秒)，默认为 120;
	 * 
	 * 当tokenLimit = 1 时,旧的Token快速失效的时间
	 * 
	 */
	private int fastExpires = 120;
    
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getBase64Secret() {
		return base64Secret;
	}
	
	public void setBase64Secret(String base64Secret) {
		this.base64Secret = base64Secret;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getExpiresSecond() {
		return expiresSecond;
	}
	
	public void setExpiresSecond(int expiresSecond) {
		this.expiresSecond = expiresSecond;
	}

	public boolean isRefleshExpires() {
		return refleshExpires;
	}

	public void setRefleshExpires(boolean refleshExpires) {
		this.refleshExpires = refleshExpires;
	}

	public int getTokenLimit() {
		return tokenLimit;
	}

	public void setTokenLimit(int tokenLimit) {
		this.tokenLimit = tokenLimit;
	}

	public int getFastExpires() {
		return fastExpires;
	}

	public void setFastExpires(int fastExpires) {
		this.fastExpires = fastExpires;
	}
}
