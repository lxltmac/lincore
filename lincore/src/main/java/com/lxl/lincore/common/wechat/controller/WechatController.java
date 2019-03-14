package com.lxl.lincore.common.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxl.common.base.ResponseResult;
import com.lxl.lincore.common.wechat.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lxltmac
 * @Description:微信授权
 * @Date: 2018/6/17 18:41
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WxService wxService;


    /**
     * 微信授权获取wechatUnionID
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "userInfo",method=RequestMethod.POST)
    public ResponseResult<JSONObject> userInfo(@RequestBody JSONObject jsonObject){
        return wxService.userInfo(jsonObject);
    }

    /**
     * 获取微信用户信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "wechatInfo",method=RequestMethod.POST)
    public ResponseResult<JSONObject> wechatInfo(@RequestBody JSONObject jsonObject) {
        return wxService.wechatInfo(jsonObject);
    }
}
