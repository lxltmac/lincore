package com.lxl.lincore.common.enmus;

/**
 * 公共枚举
 * 
 * @date 2018年11月16日 下午2:35:51
 * @author NIck
 */
public enum CommonEnum {
	/** 否 默认值 */
	DEFAULT_NO("1", "否"),
	/** 是 默认值 */
	DEFAULT_YES("2", "是"),

	;

	private String code;

	private String desc;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	private CommonEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
