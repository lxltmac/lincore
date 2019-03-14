package com.lxl.lincore.common.num;

/**
 * 业务编号
 * 
 * @author ff
 *
 */
public enum BusinessNumber {

	// 课程券编号
	COUESETICKETCODE("CT", "coueseTicketCode"),
	// 退款编号
	REFUNDNO("RN", "refundNO"),
	// 支付凭证编号
	PAYORDERNO("PN", "payOrderNO"),
	// 退款凭证编号
	REFUNDVOUCHERNO("RVN", "refundVoucherNO"),
	// 订单详情编号
	ORDERDETAILNO("ODN", "orderDetailNO"),
	// 用户课程券编号
	USERCOURSETICKETNO("UCTN", "userCourseTicketNO"),
	// 报课券编号
	COURSEEXCHANGEVOUCHERNO("CEVN", "courseExchangeVoucherNO"),
	// 用户报课券编号
	USERCOURSEEXCHANGEVOUCHERNO("UCEVN", "userCourseExchangeVoucherNO"),
	// 订单编号
	ORDERNO("ON", "orderNO"),
	// 用户编号
	SYSUSERCODE("SYSUSERCODE", "sysUserCode"),
	// 菜单编号
	MENUCODE("MU", "menuCode"),
	// 公司编号
	GMDCOMPANYCODE("", "companyCode"),
	// 角色编号
	GMDROLECODE("", "roleCode"),
	//协会编号
	ASSOCIATECODE("","associateCode"),
	;

	// 前缀
	private String preFix;
	// 缓存key
	private String redisName;

	private BusinessNumber(String preFix, String redisName) {
		this.preFix = preFix;
		this.redisName = redisName;
	}

	public String getPreFix() {
		return preFix;
	}

	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}

	public String getRedisName() {
		return redisName;
	}

	public void setRedisName(String redisName) {
		this.redisName = redisName;
	}

}
