package com.lxl.lincore.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author xiaozhiqiang
 *
 */
public class JsonUtil {
	public static JSONObject getSuccessResponse() {
		JSONObject response = new JSONObject();
		response.put("returnCode", "0");
		response.put("returnMsg", "success");
		return response;
	}
}
