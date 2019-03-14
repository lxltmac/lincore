package com.lxl.lincore.common.aliyun.video.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.lxl.lincore.common.aliyun.video.domain.dto.UploadVideoInfo;
import com.lxl.lincore.common.aliyun.video.service.VideoUploadService;
import com.lxl.common.helper.ServiceConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author linxilii
 * @Description: 视频上传凭证和上传地址
 * @Date: 2018/9/12 21:33
 */
@Service
public class VideoUploadServiceImpl implements VideoUploadService {
    private Logger logger = LoggerFactory.getLogger(VideoUploadServiceImpl.class);

    private static String accessKeyId;
    private static String accessKeySecret;

    static {
        accessKeyId = ServiceConfigHelper.getServiceConfigValue("aliyun.video.accessId");
        accessKeySecret = ServiceConfigHelper.getServiceConfigValue("aliyun.video.accessKey");
    }
    public static DefaultAcsClient initVodClient() {
        //点播服务所在的Region，国内请填cn-shanghai，不要填写别的区域
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * @Author linxilii
     * @Description: 获取视频上传信息
     * @Date: 2018/9/13
     * @param uploadVideoInfo
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject getUploadInfo(UploadVideoInfo uploadVideoInfo){
        JSONObject jsonObject = new JSONObject();
        DefaultAcsClient client = initVodClient();
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        try {
            response = createUploadVideo(client,uploadVideoInfo);
            jsonObject.put("VideoId" ,response.getVideoId());
            jsonObject.put("UploadAddress",response.getUploadAddress());
            jsonObject.put("UploadAuth", response.getUploadAuth());
        } catch (Exception e) {
            logger.error("ErrorMessage = " + e.getLocalizedMessage());
        }
        logger.info("RequestId = " + response.getRequestId());
        return jsonObject;
    }

    /**
     * @Author linxilii
     * @Description: 刷新视频上传信息
     * @Date: 2018/9/13
     * @param uploadVideoInfo
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject refreshUploadInfo(UploadVideoInfo uploadVideoInfo){
        JSONObject jsonObject = new JSONObject();
        DefaultAcsClient client = initVodClient();
        RefreshUploadVideoResponse response = new RefreshUploadVideoResponse();
        try {
            /*必选，视频源文件名称（必须带后缀, 支持 ".3gp", ".asf", ".avi", ".dat", ".dv", ".flv", ".f4v", ".gif", ".m2t", ".m3u8", ".m4v", ".mj2", ".mjpeg", ".mkv", ".mov", ".mp4", ".mpe", ".mpg", ".mpeg", ".mts", ".ogg", ".qt", ".rm", ".rmvb", ".swf", ".ts", ".vob", ".wmv", ".webm"".aac", ".ac3", ".acm", ".amr", ".ape", ".caf", ".flac", ".m4a", ".mp3", ".ra", ".wav", ".wma"）*/
            response = refreshUploadVideo(client,uploadVideoInfo);
            jsonObject.put("UploadAddress",response.getUploadAddress());
            jsonObject.put("UploadAuth", response.getUploadAuth());
        } catch (Exception e) {
            logger.error("ErrorMessage = " + e.getLocalizedMessage());
        }
        logger.info("RequestId = " + response.getRequestId());
        return jsonObject;
    }

    /*刷新视频上传凭证函数*/
    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client,UploadVideoInfo uploadVideoInfo) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(uploadVideoInfo.getVideoId());
        return client.getAcsResponse(request);
    }

    /*获取上传地址和凭证函数*/
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client, UploadVideoInfo uploadVideoInfo) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
//        request.setTitle("测试视频");
//        request.setDescription("这是一个测试时间");
//        request.setFileName("我是测试.mp4");
//        request.setCoverURL("http://img.alicdn.com/video/20180703/我是测试.mp4");
        request.setTitle(uploadVideoInfo.getTitle());
        request.setDescription(uploadVideoInfo.getDescription());
        request.setFileName(uploadVideoInfo.getFileName());
        request.setCoverURL(uploadVideoInfo.getCoverURL());
        request.setCateId(uploadVideoInfo.getCateId());
        return client.getAcsResponse(request);
    }

}
