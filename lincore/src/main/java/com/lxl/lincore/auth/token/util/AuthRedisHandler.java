package com.lxl.lincore.auth.token.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.auth.token.base.BaseToken;
import com.lxl.lincore.auth.token.base.TokenType;
import com.lxl.common.exception.AuthenticationException;
import com.lxl.common.helper.JedisLock;
import com.lxl.common.helper.RedisHelper;
import com.lxl.common.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 鉴权服务所有对Redis的所有操作处理
 * 
 * <p> 其他地方请不要单独对Redis进行操作,方便后续维护.
 * 
 * @author linxili
 *
 * @since 2017年7月8日
 *
 * @version 1.0
 */
public class AuthRedisHandler {
	
	
	private static Logger logger = LoggerFactory.getLogger(AuthRedisHandler.class);
	
	/**
	 * 鉴权服务存放在Redis中Key的定义
	 * 
	 * <p>其他地方不允许定义Redis的Key,方便后续维护
	 * 
	 * @author linxili
	 *
	 * @since 2017年7月8日
	 *
	 * @version 1.0
	 */
	private enum AuthRedisKey {

		//用于存放获取的token
		AccessToken,
		
		//用于记录同一用户Token数
		LoginUserCount,
		
		//记录用户最新的Token
		CurrentUserToken,
		
		//清除Token并发锁，防止多个服务同时执行清除操作
		ClearTokenLock,
		;
		
		
		public String getRedisKeyName(){
			return "AuthServer__"+this.name();
		}
	}

	/**
	 * 用户获取 token后,将token信息以及用户信息保存在Redis中
	 * 
	 * @author linxili
	 * @since 2017年7月8日
	 * @param user 
	 * @param accessToken
	 * @param seconds accessToken的过期时间
	 */
	public static void saveOrRefreshAccessToken(BaseToken token){
		//设置token的用户ID
		if(token == null || token.getUser() == null){
			throw new AuthenticationException("保存AccessToken对应用户信息不能为空");
		}
		
		if(token.getCreateTime() == null){
			token.setCreateTime(new Date());
		}
		
		//设置或刷新过期时间(刷新过期时间=当前时间+有效时间)
		token.setExpiresTime(new Date(new Date().getTime() + token.getExpires()*1000L));
		
		//记录用户最后获取token的信息
		RedisHelper.hset(AuthRedisKey.AccessToken.getRedisKeyName(),token.getAccessToken(), JSON.toJSONString(token));
	}
	
	
//	/**
//	 * 
//	 * 刷新用户当前的Token数
//	 * 
//	 * @param token
//	 *
//	 * @author linxili
//	 * @date 2018年1月4日
//	 */
//	public static void saveOrRefreshLoginUserCount(String user){
//		RedisHelper.hincrBy(AuthRedisKey.LoginUserCount.getRedisKeyName(), user, 1);
//	}
	
//	/**
//	 * 
//	 * 获取当前用户Token数
//	 * 
//	 * @param token
//	 *
//	 * @author linxili
//	 * @date 2018年1月4日
//	 */
//	public static int getLoginUserCount(String user){
//		String count = RedisHelper.hget(AuthRedisKey.LoginUserCount.getRedisKeyName(), user);
//		
//		return count == null ? 0 : Integer.valueOf(count);
//	}
	
	
	/**
	 * 
	 * 保存用户
	 * 
	 * @param token
	 *
	 * @author linxili
	 * @date 2018年1月4日
	 */
	public static void refreshCurrentUserToken(String user,String accessToken){
		RedisHelper.hset(AuthRedisKey.CurrentUserToken.getRedisKeyName(), user ,accessToken);
	}
	
	/**
	 * 设置当前用户之前的Token快速过期
	 * 
	 * @param user
	 * @param fastExpires
	 *
	 * @author linxili
	 * @date 2018年1月5日
	 */
	public static void setOldAccessTokenFastExpires(String user,int fastExpires){
		
		String accessToken = RedisHelper.hget(AuthRedisKey.CurrentUserToken.getRedisKeyName(), user);
		
		if(accessToken == null){
			return;
		}
		
		BaseToken token = getTokenValue(accessToken);
		
		if(token == null){
			return;
		}
		
		//重新设置过期时间 = 快速过期时间
		token.setExpires(fastExpires);
		
		//刷新Token
		saveOrRefreshAccessToken(token);
	}
	
	/**
	 * 获取 tokenvalue
	 * 
	 * @author linxili
	 * @since 2017年8月31日
	 * @param accessToken
	 * @return
	 */
	public static BaseToken getTokenValue(String accessToken){
		String tokenValue =  RedisHelper.hget(AuthRedisKey.AccessToken.getRedisKeyName(),accessToken);
		
		
		if(DataUtil.isEmpty(tokenValue)){
			return null;
		}

		//解析通用类型，获取tokenType
		BaseToken token = JSON.parseObject(tokenValue,BaseToken.class);
		
		//根据tokenType获取具体类型
		Class<? extends BaseToken> t = TokenType.getTokenClass(token.getTokenType());
		
		token = JSON.parseObject(tokenValue,t);
		
		return token;
	}
	
	/**
	 * 获取 tokenvalue
	 * 
	 * @author linxili
	 * @since 2017年8月31日
	 * @param accessToken
	 * @return
	 */
	public static <T> T getTokenValue(String accessToken,Class<T> clazz){
		String tokenValue =  RedisHelper.hget(AuthRedisKey.AccessToken.getRedisKeyName(),accessToken);
		
		
		if(DataUtil.isEmpty(tokenValue)){
			return null;
		}

		//解析通用类型，获取tokenType
		T token = JSON.parseObject(tokenValue,clazz);
		
		return token;
	}
	
	/**
	 * 校验token是否有效,
	 * 如果无效,则抛出无效token异常,
	 * 如果有效,则重置token的效时间
	 * 
	 * @author linxili
	 * @since 2017年7月8日
	 * @param accessToken
	 */
	public static BaseToken checkAccessToken(String accessToken){
		if (DataUtil.isEmpty(accessToken)) {
			throw new AuthenticationException("请求TOKEN不存在");
		}
		
		logger.info("当前校验Token ： " + accessToken );
		
		BaseToken token = getTokenValue(accessToken);
		
		if(DataUtil.isEmpty(token)){
			throw new AuthenticationException("无效token,请重新获取token.");
		}
		
		int expires = token.getExpires();
		
		//永不过期
		if(expires < 0){
			return token;
		}
		
		//判断token是否过期
		//已过期
		if(new Date().compareTo(token.getExpiresTime()) > 0){
			//将过期的Token删除
			RedisHelper.hdelete(AuthRedisKey.AccessToken.getRedisKeyName(), accessToken);
			//同时抛出Token失效异常信息
			throw new AuthenticationException("token已失效,请重新获取token.");
		}
		
		//是否刷新token的有效时间
		boolean reflesh = token.getRefleshExpires() == null ? true : token.getRefleshExpires();
		
		if(reflesh){
			saveOrRefreshAccessToken(token);
		}
		
		return token;
	}
	
	
	/**
	 * 注销token
	 * 
	 * @author linxili
	 * @since 2017年7月8日
	 * @param accessToken
	 */
	public static void logout(String accessToken){
		try{
			RedisHelper.hdelete(AuthRedisKey.AccessToken.getRedisKeyName(), accessToken);
		}catch(Exception e){
			//Redis中不存在当前的 accessToken
		}
	}
	
	public static JSONObject getTokenInfo(BaseToken token){
		JSONObject result = new JSONObject();
		
		if(token.getUser() != null){
			
			String currentToken = RedisHelper.hget(AuthRedisKey.CurrentUserToken.getRedisKeyName(), token.getUser());
			
			if(currentToken != null){
				BaseToken tokenValue = getTokenValue(currentToken); 
				//当前用户Token
				result.put("userToken", tokenValue);
			}else{
				result.put("userToken", "当前用户无Token");
			}
		}
		
		//对应Token
		if(token.getAccessToken() != null){
			BaseToken tokenValue = getTokenValue(token.getAccessToken()); 
			//当前用户Token
			if(tokenValue != null){
				result.put("AccessToken", tokenValue);
			}else{
				result.put("AccessToken", "该Token已失效过期被删除");
			}
			
		}
		
		return result;
	}
	
	/**
	 * @Description 根据field删除token
	 * @author linxili
	 * @param field
	 * @time:2018年10月22日
	 */
	public static void deleteAcessTokenByUserCode(String field){
		//将过期的Token删除
		RedisHelper.hdelete(AuthRedisKey.AccessToken.getRedisKeyName(), field);
	}
	
	/**
	 * @Description 根据field获取value
	 * @author linxili
	 * @param field
	 * @return String
	 * @time:2018年10月22日
	 */
	public static String getCurrentUserTokenByField(String field) {
		
		return RedisHelper.hget(AuthRedisKey.CurrentUserToken.getRedisKeyName(), field);
	}
	
	/**
	 * 
	 * 清除无效的AccessToken
	 * @return count 清除无效的Token的条数
	 */
	public static int clearInvalidAccessToken() {
		int count = 0;
		JedisLock jLock = new JedisLock(AuthRedisKey.ClearTokenLock.getRedisKeyName(), RedisHelper.getJedisCluster());
		// 加锁处理
		try {
			if (jLock.lock()) {
				Map<String, String> hgetAll = RedisHelper.hgetAll(AuthRedisKey.AccessToken.getRedisKeyName());
				if (hgetAll == null || hgetAll.isEmpty()) {
					return 0;
				}
				for (Map.Entry<String, String> entry : hgetAll.entrySet()) {
					String accessToken = entry.getKey();
					// 解析通用类型
					BaseToken token = JSON.parseObject(entry.getValue(), BaseToken.class);
					int expires = token.getExpires();
					// 永不过期
					if (expires < 0) {
						continue;
					}
					// 判断token是否过期
					// 将过期的Token删除
					if (token.getExpiresTime() == null || new Date().compareTo(token.getExpiresTime()) > 0) {
						RedisHelper.hdelete(AuthRedisKey.AccessToken.getRedisKeyName(), accessToken);
						count++;
					}
				}
				return count;
			}
		} finally {
			jLock.unlock();
		}
		return count;
	}
	
//	/**
//	 * 
//	 * 清除无效的AccessToken
//	 * @return count 清除无效的Token的条数
//	 */
//	public static int clearInvalidAccessToken() {
//		
//		String clearTokenLock = RedisHelper.get(AuthRedisKey.ClearTokenLock.getRedisKeyName());
//		
//		if("1".equals(clearTokenLock)) {
//			return 0;
//		}
//		
//		//设置Lock
//		RedisHelper.set(AuthRedisKey.ClearTokenLock.getRedisKeyName(),"1");
//		
//		int count = 0;
//		
//		try {
//			Map<String, String> hgetAll = RedisHelper.hgetAll(AuthRedisKey.AccessToken.getRedisKeyName());
//			
//			if(hgetAll == null || hgetAll.isEmpty()) {
//				return 0;
//			}
//			
//			for(Map.Entry<String, String> entry : hgetAll.entrySet()) {
//				String accessToken = entry.getKey();
//				//解析通用类型
//				BaseToken token = JSON.parseObject(entry.getValue(),BaseToken.class);
//				
//				int expires = token.getExpires();
//				
//				//永不过期
//				if(expires < 0){
//					continue;
//				}
//				
//				//判断token是否过期
//				//将过期的Token删除
//				if(token.getExpiresTime() == null || new Date().compareTo(token.getExpiresTime()) > 0){
//					RedisHelper.hdelete(AuthRedisKey.AccessToken.getRedisKeyName(), accessToken);
//					count++;
//				}
//			}
//		}catch (Exception e) {
//			
//		}finally {//释放锁
//			RedisHelper.set(AuthRedisKey.ClearTokenLock.getRedisKeyName(),"0");
//		}
//		
//		return count;
//	}

}
