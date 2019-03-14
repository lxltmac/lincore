package com.lxl.lincore.auth.common.constant;

public enum ServerErrorCode {
	TOKEN_USERPASSWORD("1001","用户或密码错误")
	;
	
	private String code;
	
	private String message;
	
	private ServerErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code ;
	}

	public String getMessage() {
		return message;
	}
}
