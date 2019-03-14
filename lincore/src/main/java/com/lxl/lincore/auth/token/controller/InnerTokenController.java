package com.lxl.lincore.auth.token.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.auth.token.api.ApiToken;
import com.lxl.lincore.auth.token.api.ApiTokenService;
import com.lxl.lincore.auth.token.base.BaseToken;
import com.lxl.lincore.auth.token.base.TokenFactory;
import com.lxl.lincore.auth.token.base.TokenService;
import com.lxl.lincore.auth.token.base.TokenType;
import com.lxl.lincore.auth.token.util.AuthRedisHandler;
import com.lxl.common.base.ResponseResult;
import com.lxl.common.constant.ErrorCode;
import com.lxl.common.exception.DataNotFoundException;
import com.lxl.common.exception.ParameterException;
import com.lxl.common.helper.ServiceConfigHelper;
import com.lxl.common.helper.ValidateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token请求处理(供内部服务间调用)
 * 
 * @author linxili
 *
 * @since 2017年8月1日
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/oauth/token")
public class InnerTokenController {

	private static Logger logger = LoggerFactory.getLogger(InnerTokenController.class);
	
	@Autowired
	private ApiTokenService apiAccessTokenService;


//	/**
//	 * 用户登陆鉴权
//	 *
//	 * @param loginParam
//	 * @return
//	 */
//	@RequestMapping(value = "getAccessToken", method = RequestMethod.POST)
//	public Object getAccessToken(@RequestBody JSONObject request) {
//
//		// 校验参数
//		ValidateHelper.validateNull(request, new String[] { "clientId" });
//
//		request.put("moduleCode", request.getString("clientId"));
//		request.put("validInd", "1");
//
//		// 校验 modelCode服务是否是使用手机号登陆的app应用
//		ResponseResult<JSONObject> result = authUserClient.findModule(request);
//
//		// 返回错误
//		if (!ErrorCode.SUCCESS.getCode().equals(result.getErrorCode())) {
//			return result;
//		}
//
//		JSONObject module = result.getData();
//
//		if (module == null) {
//			throw new DataNotFoundException("获取token校验失败,客户端标识不存在。");
//		}
//
//		String type = module.getString("type");
//
//		// 根据类型获取具体的会话类型
//		Class<? extends BaseToken> t = TokenType.getTokenClass(type);
//
//		// 获取具体的服务
//		TokenService accessTokenServie = TokenFactory.getService(type);
//
//		// 创建会话
//		BaseToken token = accessTokenServie.create(JSONObject.toJavaObject(request, t));
//
//		return new ResponseResult<Object>(token);
//	}
	/**
	 * 用户登陆鉴权
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAccessToken", method = RequestMethod.POST)
	public Object getAccessToken(@RequestBody JSONObject request) {

		// 校验参数
		ValidateHelper.validateNull(request, new String[] { "clientId" });
        String clientId = request.getString("clientId");

		String type = ServiceConfigHelper.getServiceConfigValue("module."+clientId+".type");
		if (com.lxl.common.util.StringUtils.isEmptyStr(type)) {
			throw new DataNotFoundException("获取token校验失败,客户端标识不存在。");
		}
		// 根据类型获取具体的会话类型
		Class<? extends BaseToken> t = TokenType.getTokenClass(type);

		// 获取具体的服务
		TokenService accessTokenServie = TokenFactory.getService(type);

		// 创建会话
		BaseToken token = accessTokenServie.create(JSONObject.toJavaObject(request, t));

		return new ResponseResult<Object>(token);
	}
	
	/**
	 * 网关服务,获取Token。
	 * 
	 * 用户信息由网关服务进行单独管理, 该服务只负责生成以及校验Token信息
	 * 
	 * @param token
	 * @return
	 *
	 * @author linxili
	 * @date 2017年12月26日
	 */
	@RequestMapping(value = "getApiToken", method = RequestMethod.POST)
	public Object getAccessToken(@RequestBody ApiToken token) {

		// 校验参数
		ValidateHelper.validateNull(token, new String[] { "userCode" });

		BaseToken accessToken = apiAccessTokenService.create(token);

		return new ResponseResult<Object>(accessToken);
	} 
	

	/**
	 * 提供远程服务调用，验证token合法性
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkAccessToken", method = RequestMethod.POST)
	public Object checkAccessToken(@RequestBody BaseToken request) {

		ValidateHelper.validateNull(request, new String[] { "accessToken" });

		// 校验token是否在Redis中存在
		AuthRedisHandler.checkAccessToken(request.getAccessToken());

		return new ResponseResult<Object>();
	}

	/**
	 * 
	 * 获取管理台登陆用户信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLoginUser", method = RequestMethod.POST)
	public Object getLoginUser(@RequestBody BaseToken request) {

		ValidateHelper.validateNull(request, new String[] { "accessToken" });

		// 校验token是否在Redis中存在
		BaseToken userToken = AuthRedisHandler.checkAccessToken(request.getAccessToken());

		return new ResponseResult<Object>(userToken);
	}

	/**
	 * 注消登陆,清除session中的token信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public ResponseResult<Object> logout(@RequestBody BaseToken token) {

		if (token == null || StringUtils.isEmpty(token.getAccessToken())) {
			throw new ParameterException("token 参数不能为空! ");
		}

		logger.info("token注销成功===> " + token);

		// 注销
		AuthRedisHandler.logout(token.getAccessToken());

		return new ResponseResult<Object>(ErrorCode.SUCCESS.getCode(),"token注销成功.");
	}

	
	/**
	 * 测试
	 * 
	 * @param token
	 * @return
	 *
	 * @author linxili
	 * @date 2018年1月5日
	 */
	@RequestMapping(value = "getTotenInfo", method = RequestMethod.POST)
	public ResponseResult<Object> getTotenInfo(@RequestBody BaseToken token) {
		return new ResponseResult<Object>(AuthRedisHandler.getTokenInfo(token));
	}
}
