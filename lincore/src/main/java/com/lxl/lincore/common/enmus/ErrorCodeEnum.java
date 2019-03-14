package com.lxl.lincore.common.enmus;

/**
 * 错误代码枚举类
 * 
 * @date 2018年11月16日 下午5:43:06
 * @author NIck
 */
public enum ErrorCodeEnum {

	// 1000-1999 用户相关
	/** 用户信息未找到,请重试 */
	USER_DATA_NOT_FOUND("modeule_1001", "用户信息未找到,请重试"),
	/** 你已经关注过TA啦 */
	ALREADY_FOLLOW("modeule_1002", "你已经关注过TA啦"),

	/** 此手机号已被其它微信绑定 */
	// 2000-2999 微信模块 (用到其他的模块往下加,错误码不可重复)
	WX_MP_ERROR("modeule_2000", "微信公众账号方面错误"),
	/** 通过APP端的授权code获取用户的微信信息失败,请重试 */
	GET_WX_USER_INFO_ERROR("modeule_2001", "通过APP端的授权code获取用户的微信信息失败,请重试"),

	// 3000-3999 协会模块
	/** 获取协会职位信息失败,请重试 */
	INSTITUTE_POSITION_DATA_NOT_FOUND("modeule_3000", "获取用户协会职位信息失败,请重试"),
	/** 您已关注协会，无需操作 */
	ASSOCIATEFOLLOW_IS_EXIST("modeule_3001", "您已关注协会，无需操作"),
	/** 您在该协会尚无职位信息,无法报名 */
	ASSOCIATEPOS_DATA_NOT_FOUND("modeule_3002", "您在该协会尚无职位信息,无法报名"),

	// 4000-4999 活动模块
	/** 获取活动信息失败,请重试 */
	ACTIVITYINFO_DATA_NOT_FOUND("modeule_4000", "获取活动信息失败,请重试"),
	/** 您的等级不符合活动报名规则,无法报名 */
	YOUR_LEVEL_NOT_SATISFY_ACTIVITY_RULE("modeule_4001", "您的等级不符合活动报名规则,无法报名"),
	/** 获取活动价格或用户优惠比例失败,请重试 */
	ACTIVITYINFO_PRICE_OR_USER_PROPORTION_DATA_NOT_FOUND("modeule_4002",
			"获取活动价格或用户优惠比例失败,请重试"),
	/** 您还没有关注该协会,请先关注 */
	USER_NOT_FOLLOW_ASSOCIATIONDYNAMICINFODETAIL("modeule_4003",
			"您还没有关注该协会,请先关注"),
	/** 当前活动报名人数已经满足,无法继续报名 */
	ACTIVITY_QUOTA_ALREADY_FULL("modeule_4004", "当前活动报名人数已经满足,无法继续报名"),
	/** 校验活动信息或用户信息失败 */
	CHECK_USER_OR_ACTIVITY_FAIL("modeule_4005", "校验活动信息或用户信息失败"),
	/** 您总共需要支付0元 */
	TOTAL_PAYMENT_REQUIRED_0("modeule_4006", "您总共需要支付0元"),
	/** 报名活动失败 */
	SIGN_FAIL("modeule_4007", "报名活动失败"),
	/** 您已成功报名该活动或报名审核进行中,请勿重复操作 */
	ALREADY_SIGN_THIS_ACTIVITY_OR_AUDIT_ING("modeule_4008", "您已成功报名该活动或报名审核进行中,请勿重复操作"),

	// 5000-5999 协会动态模块
	/** 协会动态详情信息不存在 */
	ASSOCIATIONDYNAMICINFODETAIL_DATA_NOT_FOUND("modeule_5000", "协会动态详情信息不存在"),
	/** 协会动态信息不存在 */
	ASSOCIATIONDYNAMICINFO_DATA_NOT_FOUND("modeule_5001", "协会动态信息不存在"),
	/** 协会关注记录列表获取失败,请重试 */
	ASSOCIATEFOLLOW_DATA_NOT_FOUND("modeule_5002", " 协会关注记录列表获取失败,请重试 "),
	// 6000-6999 资讯模块
	/** 资讯信息不存在 */
	CONSULTINFO_DATA_NOT_FOUND("modeule_6000", "资讯信息不存在"),

	// 7000-7999 评论模块
	/** 评论已存在，无法进行评论 */
	COMMENT_DATA_EXIST("modeule_7000", "您已评论过，无法继续评论！"),
	/** 该用户留言已存在，无法进行留言 */
	ASSOCIATECOMMENT_DATA_EXIST("modeule_7001", "您已留言过，无法继续留言！"),

	// 8000-8999 系统模块
	/** 角色信息未找到,请重试 */
	ROLERRIVILEGE_NOTEXIST("module_8001", "权限列表不能为空"),

	// 9000-9999 订单模块
	/** 角色信息未找到,请重试 */
	ROLE2RRIVILEGE_NOTEXIST("module_8001", "权限列表不能为空"),

	;
	private String code;

	private String desc;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	private ErrorCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}