package com.lxl.lincore.common.num;

import com.lxl.common.helper.RedisHelper;
import com.lxl.common.util.DateUtils;

import java.util.Date;

/**
 * 生成编号
 * 
 * @author obba
 *
 */
public class GenerateBusinessNo {

	private final static String dateFormat = "yyyyMMdd";

	private final static int defaultLength = 10;

	private final static int SYSUSERCODEDEFAULTLENGTH = 4;

	private final static int MENUCODEDEFAULTLENGTH = 4;

	private final static int ASSOCIATECODEDEFAULTLENGTH = 6;
	
	private final static int GMDCOMPANYCODEDEFAULTLENGTH = 4;
	
	private final static int GMDROLECODEDEFAULTLENGTH = 2;

	/** 当前日期 */
	private static String today() {
		return DateUtils.formatString(new Date(), dateFormat);
	}

	/** 当前日期 */
	private static String todayTime() {
		return DateUtils.formatString(new Date(), dateFormat);
	}

	/** 流水号补零 */
	private static String padIndex(long index, int length) {
		return String.format("%0" + length + "d", index);
	}

	/** 生成编号 */
	public static String buildNumber(BusinessNumber businessNumber) {
		Long index = RedisHelper.incr(businessNumber.getRedisName());

		return businessNumber.getPreFix() + today() + padIndex(index, defaultLength);
	}

	public static String buildCoueseTicketCode() {
		return buildNumber(BusinessNumber.COUESETICKETCODE);
	}

	public static String buildRefundNO() {
		return buildNumber(BusinessNumber.REFUNDNO);
	}

	public static String buildPayOrderNO() {
		return buildNumber(BusinessNumber.PAYORDERNO);
	}

	public static String buildOrderDetailNO() {
		return buildNumber(BusinessNumber.ORDERDETAILNO);
	}

	public static String buildUserCourseTicketNO() {
		return buildNumber(BusinessNumber.USERCOURSETICKETNO);
	}

	public static String buildTargetNumber() {
		return buildNumber(BusinessNumber.COURSEEXCHANGEVOUCHERNO);
	}

	public static String buildUserCourseExchangeVoucherNO() {
		return buildNumber(BusinessNumber.USERCOURSEEXCHANGEVOUCHERNO);
	}

	public static String buildOrderNO() {
		return buildNumber(BusinessNumber.ORDERNO);
	}

	/** 生成报课券编号 */
	public static String buildCourseExchangeVoucherNO() {
		return buildNumber(BusinessNumber.COURSEEXCHANGEVOUCHERNO);
	}

	/** 生成退款凭证号 */
	public static String buildRefundVoucherNO() {
		return buildNumber(BusinessNumber.REFUNDVOUCHERNO);
	}

	/** 系统用户 */
	/** 生成用户编号 */
	public static String buildSysUserCode(String companyCode) {
		return buildSysUserNO(BusinessNumber.SYSUSERCODE, companyCode);
	}

	/** 用户生成用户编号 */
	public static String buildSysUserNO(BusinessNumber businessNumber, String companyCode) {
		Long index = RedisHelper.incr(businessNumber.getRedisName());

		return companyCode + padIndex(index, SYSUSERCODEDEFAULTLENGTH);
	}

	/** 首页菜单 */
	/** 生成菜单编号 */
	public static String buildMenuCode() {
		return buildMenuNO(BusinessNumber.MENUCODE);
	}

	/** 菜单生成编号 */
	public static String buildMenuNO(BusinessNumber businessNumber) {
		Long index = RedisHelper.incr(businessNumber.getRedisName());

		return businessNumber.getPreFix() + padIndex(index, MENUCODEDEFAULTLENGTH);
	}
	
	/** 生成公司编号 */
	public static String buildCompanyCode(String companyCode) {
		return buildCompanyNO(BusinessNumber.GMDCOMPANYCODE,companyCode);
	}

	/** 公司生成编号 */
	public static String buildCompanyNO(BusinessNumber businessNumber, String companyCode) {
		Long index = RedisHelper.incr(businessNumber.getRedisName());

		return companyCode + padIndex(index, GMDROLECODEDEFAULTLENGTH);
	}
	

	/** 生成角色编号 */
	public static String buildRoleCode(String companyCode) {
		return buildRoleNO(BusinessNumber.GMDROLECODE, companyCode);
	}

	/** 角色生成编号 */
	public static String buildRoleNO(BusinessNumber businessNumber, String companyCode) {
		Long index = RedisHelper.incr(businessNumber.getRedisName());

		return companyCode + padIndex(index, GMDROLECODEDEFAULTLENGTH);
	}

	/** 生成协会编号 */
	public static String buildAssociateCode() {
		return buildAssociateNO(BusinessNumber.ASSOCIATECODE);
	}

	/** 生成协会编号 */
	public static String buildAssociateNO(BusinessNumber businessNumber) {
		Long index = RedisHelper.incr(businessNumber.getRedisName());

		return businessNumber.getPreFix() + padIndex(index, ASSOCIATECODEDEFAULTLENGTH);
	}
}
