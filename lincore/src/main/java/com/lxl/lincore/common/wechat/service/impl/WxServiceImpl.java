package com.lxl.lincore.common.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxl.common.base.ResponseResult;
import com.lxl.lincore.common.enmus.ErrorCodeEnum;
import com.lxl.common.exception.BusinessException;
import com.lxl.lincore.common.wechat.service.WxService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author linxilii
 * @Description:
 * @Date: 2018/9/6 9:57
 */
@Service
@Transactional
public class WxServiceImpl implements WxService {
    @Autowired
    private WxMpService wxOpenService;

    private static final Logger logger =  LoggerFactory.getLogger(WxServiceImpl.class);

    /**
     * 微信授权获取wechatUnionID
     * @param jsonObject
     * @return
     */
    public ResponseResult<JSONObject> userInfo(JSONObject jsonObject){
        String code = jsonObject.getString("code");
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            //获取access_token
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            logger.error("【微信网页授权】{}", e);
            throw new BusinessException(ErrorCodeEnum.WX_MP_ERROR.getCode(), ErrorCodeEnum.WX_MP_ERROR.getDesc());
        }
        logger.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
        //获取unionid
        String unionid = wxMpOAuth2AccessToken.getUnionId();

        JSONObject result = new JSONObject();
        result.fluentPut("wechatUnionID",unionid);

        return new ResponseResult<JSONObject>(result);
    }

    /**
     * 获取微信用户信息
     * @param jsonObject
     * @return
     */
    public ResponseResult<JSONObject> wechatInfo(JSONObject jsonObject){
        String code = jsonObject.getString("code");

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        WxMpUser oauth2getUserInfo = new WxMpUser();
        try {
            //获取access_token
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
            oauth2getUserInfo = wxOpenService.oauth2getUserInfo(wxMpOAuth2AccessToken,"zh_CN");
        } catch (WxErrorException e) {
            logger.error("【微信网页授权】{}", e);
            throw new BusinessException(ErrorCodeEnum.WX_MP_ERROR.getCode(), ErrorCodeEnum.WX_MP_ERROR.getDesc());
        }
        logger.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);

        //获取微信用户信息
        String openId = oauth2getUserInfo.getOpenId();
        String unionid = oauth2getUserInfo.getUnionId();
        Integer sex = oauth2getUserInfo.getSex();
        String city = oauth2getUserInfo.getCity();
        String province = oauth2getUserInfo.getProvince();
        String country = oauth2getUserInfo.getCountry();
        String headImgUrl = oauth2getUserInfo.getHeadImgUrl();
        String nickname = oauth2getUserInfo.getNickname();

        JSONObject result = new JSONObject();
        result.fluentPut("openid",openId);
        result.fluentPut("unionid",unionid);
        result.fluentPut("sex",sex);
        result.fluentPut("city",city);
        result.fluentPut("province",province);
        result.fluentPut("country",country);
        result.fluentPut("headImgUrl",headImgUrl);
        result.fluentPut("nickname",nickname);

        return new ResponseResult<JSONObject>(result);
    }
}
