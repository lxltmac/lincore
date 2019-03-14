package com.lxl.lincore.common.aliyun.video.service;

import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.common.aliyun.video.domain.dto.UploadVideoInfo;

/**
 * @author linxilii
 * @Description:
 * @Date: 2018/9/13 15:34
 */
public interface VideoUploadService {

    /**
     * @Author linxilii
     * @Description: 获取视频上传信息
     * @Date: 2018/9/13
     * @param uploadVideoInfo
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject getUploadInfo(UploadVideoInfo uploadVideoInfo);

    /**
     * @Author linxilii
     * @Description: 刷新视频上传信息
     * @Date: 2018/9/13
     * @param uploadVideoInfo
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject refreshUploadInfo(UploadVideoInfo uploadVideoInfo);
}
