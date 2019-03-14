package com.lxl.lincore.common.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.lxl.common.base.ResponseResult;

/**
 * @author linxilii
 * @Description:
 * @Date: 2018/9/6 9:56
 */
public interface WxService {

    /**
     * 微信授权获取wechatUnionID
     * @param jsonObject
     * @return
     */
    public ResponseResult<JSONObject> userInfo(JSONObject jsonObject);

    /**
     * 获取微信用户信息
     * @param jsonObject
     * @return
     */
    public ResponseResult<JSONObject> wechatInfo(JSONObject jsonObject);
}
