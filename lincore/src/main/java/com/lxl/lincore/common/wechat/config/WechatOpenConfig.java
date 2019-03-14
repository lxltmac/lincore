package com.lxl.lincore.common.wechat.config;

import com.lxl.common.helper.ServiceConfigHelper;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author lxltmac
 * @Description: 微信开放平台配置
 * @Date: 2018/6/19 21:03
 */
@Component
public class WechatOpenConfig {
    /**
     * 微信AppId
     */
    private static String openAppId = ServiceConfigHelper.getServiceConfigValue("wechat.openAppId");
    /**
     * 微信密钥
     */
    private static String openAppSecret = ServiceConfigHelper.getServiceConfigValue("wechat.openAppSecret");

    /**
     * @Description： 配置微信开发平台
     * @return
     */
    @Bean
    public WxMpService wxOpenService() {
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    /**
     * @Description： 设置微信开发平台的appid和Secret
     * @return
     */
    @Bean
    private WxMpConfigStorage wxOpenConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(openAppId);
        wxMpInMemoryConfigStorage.setSecret(openAppSecret);
        return wxMpInMemoryConfigStorage;
    }
}
