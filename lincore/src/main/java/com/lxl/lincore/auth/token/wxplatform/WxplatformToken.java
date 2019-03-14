package com.lxl.lincore.auth.token.wxplatform;

import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.auth.token.base.BaseToken;

import java.util.List;

/**
 * 
 * 系统登陆访问信息
 * 
 * @author linxili
 *
 * @since 2017年3月31日
 *
 * @version 1.0
 */
public class WxplatformToken extends BaseToken {
	
	
	
	/**
	 * 用户登陆帐号 
	 */
	private String userCode;
	
	/**
	 * 用户登陆密码
	 */
	private String password;
	
	/**
	 * 校验码
	 */
	private String captchaCode;
	
	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 归属机构/渠道
	 */
	private String channelCode;

	/**
	 * 渠道名称
	 */
	private String channelName;

	/**
	 * 
	 * 用户类型:
	 * 
	 * 1 内部用户 2经纪公司 3保险公司 4营销渠道
	 */
	private String userType;

	/**
	 * 团队代码
	 */
	private String teamCode;

	/**
	 * 团队名称
	 */
	private String teamName;

	/**
	 * 是否团队长
	 */
	private String leaderInd;
	
	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 是否需要加载团队成员标识
	 */
	private String loadTeamInd;
	
	/**
	 * 授权标识
	 */
	private String authedInd;
	
	private List<JSONObject> teamUserList;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getLeaderInd() {
		return leaderInd;
	}

	public void setLeaderInd(String leaderInd) {
		this.leaderInd = leaderInd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLoadTeamInd() {
		return loadTeamInd;
	}

	public void setLoadTeamInd(String loadTeamInd) {
		this.loadTeamInd = loadTeamInd;
	}

	public List<JSONObject> getTeamUserList() {
		return teamUserList;
	}

	public void setTeamUserList(List<JSONObject> teamUserList) {
		this.teamUserList = teamUserList;
	}

	public String getAuthedInd() {
		return authedInd;
	}

	public void setAuthedInd(String authedInd) {
		this.authedInd = authedInd;
	}
}
