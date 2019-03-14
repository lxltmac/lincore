package com.lxl.lincore.common.aliyun.video.service.impl;
//package com.wsh.common.aliyun.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.aliyun.vod.upload.req.UploadStreamRequest;
//import com.aliyun.vod.upload.resp.*;
//import com.wsh.common.aliyun.domain.dto.UploadVideoInfo;
//import com.wsh.common.aliyun.service.UploadVideoService;
//import com.wsh.common.helper.ServiceConfigHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import com.aliyun.vod.upload.impl.UploadVideoImpl;
//import com.aliyun.vod.upload.req.UploadFileStreamRequest;
//import com.aliyun.vod.upload.req.UploadURLStreamRequest;
//import com.aliyun.vod.upload.req.UploadVideoRequest;
//
///**
// * @author linxilii
// * @Description: 视频上传点播
// * @Date: 2018/9/12 10:26
// */
//@Service
//public class UploadVideoServiceImpl implements UploadVideoService {
//    private Logger logger = LoggerFactory.getLogger(UploadVideoServiceImpl.class);
//
//    private static String accessKeyId;
//    private static String accessKeySecret;
//
//    static {
//        accessKeyId = ServiceConfigHelper.getServiceConfigValue("aliyun.video.accessId");
//        accessKeySecret = ServiceConfigHelper.getServiceConfigValue("aliyun.video.accessKey");
//    }
//
//    /**
//     * 本地文件上传接口
//     * @param param
//     */
//    public JSONObject uploadVideo(UploadVideoInfo param) {
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, param.getTitle(), param.getFileName());
//        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
//        request.setPartSize(1 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
//        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
//        request.setEnableCheckpoint(false);
//        /* OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位: 毫秒，默认为300000毫秒*/
//        //request.setSlowRequestsThreshold(300000L);
//        /* 可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
//        //request.setSlowRequestsThreshold(300000L);
//        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
//        //request.setIsShowWaterMark(true);
//        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
//        //request.setCallback("http://callback.sample.com");
//        /* 视频分类ID(可选) */
//        //request.setCateId(0);
//        /* 视频标签,多个用逗号分隔(可选) */
//        //request.setTags("标签1,标签2");
//        /* 视频描述(可选) */
//        //request.setDescription("视频描述");
//        /* 封面图片(可选) */
//        //request.setCoverURL("http://cover.sample.com/sample.jpg");
//        /* 模板组ID(可选) */
//        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
//        /* 存储区域(可选) */
//        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//        logger.info("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        JSONObject jsonObject = this.getVieoInfo(response);
//        logger.error("response:" + JSON.toJSONString(response));
//        return jsonObject;
//    }
//
//    /**
//     * 网络流上传接口
//     * @param param
//     */
//    public JSONObject uploadURLStream(UploadVideoInfo param) {
//        UploadURLStreamRequest request = new UploadURLStreamRequest(accessKeyId, accessKeySecret, param.getTitle(), param.getFileName(), param.getUrl());
//        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
//        //request.setShowWaterMark(true);
//        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
//        //request.setCallback("http://callback.sample.com");
//        /* 视频分类ID(可选) */
//        //request.setCateId(0);
//        /* 视频标签,多个用逗号分隔(可选) */
//        //request.setTags("标签1,标签2");
//        /* 视频描述(可选) */
//        //request.setDescription("视频描述");
//        /* 封面图片(可选) */
//        //request.setCoverURL("http://cover.sample.com/sample.jpg");
//        /* 模板组ID(可选) */
//        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
//        /* 存储区域(可选) */
//        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadURLStreamResponse response = uploader.uploadURLStream(request);
//        logger.info("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
//        JSONObject jsonObject = this.getVieoInfo(response);
//        logger.error("response:" + JSON.toJSONString(response));
//        return jsonObject;
//    }
//
//    /**
//     * 文件流上传接口
//     * @param param
//     */
//    public JSONObject uploadFileStream(UploadVideoInfo param) {
//        UploadFileStreamRequest request = new UploadFileStreamRequest(accessKeyId, accessKeySecret, param.getTitle(), param.getFileName());
//        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
//        //request.setShowWaterMark(true);
//        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
//        //request.setCallback("http://callback.sample.com");
//        /* 视频分类ID(可选) */
//        //request.setCateId(0);
//        /* 视频标签,多个用逗号分隔(可选) */
//        //request.setTags("标签1,标签2");
//        /* 视频描述(可选) */
//        //request.setDescription("视频描述");
//        /* 封面图片(可选) */
//        //request.setCoverURL("http://cover.sample.com/sample.jpg");
//        /* 模板组ID(可选) */
//        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
//        /* 存储区域(可选) */
//        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadFileStreamResponse response = uploader.uploadFileStream(request);
//        logger.info("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
//        JSONObject jsonObject = this.getVieoInfo(response);
//        logger.error("response:" + JSON.toJSONString(response));
//        return jsonObject;
//    }
//
//    /**
//     * 流式上传接口
//     *
//     * @param param
//     */
//    public JSONObject uploadStream(UploadVideoInfo param) {
//        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, param.getTitle(), param.getFileName(), param.getInputStream());
//        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
//        //request.setShowWaterMark(true);
//        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
//        //request.setCallback("http://callback.sample.com");
//        /* 视频分类ID(可选) */
//        //request.setCateId(0);
//        /* 视频标签,多个用逗号分隔(可选) */
//        //request.setTags("标签1,标签2");
//        /* 视频描述(可选) */
//        //request.setDescription("视频描述");
//        /* 封面图片(可选) */
//        //request.setCoverURL("http://cover.sample.com/sample.jpg");
//        /* 模板组ID(可选) */
//        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
//        /* 存储区域(可选) */
//        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadStreamResponse response = uploader.uploadStream(request);
//        logger.info("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        JSONObject jsonObject = this.getVieoInfo(response);
//        return jsonObject;
//    }
//
//
//    /**
//     * @Author linxilii
//     * @Description: 获取视频上传返回信息
//     * @Date: 2018/9/12
//     * @param response
//     * @return: com.alibaba.fastjson.JSONObject
//     **/
//    private JSONObject getVieoInfo(BaseResponse response){
//        JSONObject jsonObject = new JSONObject();
//        if (response.isSuccess()) {
//            logger.info("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            logger.info("VideoId=" + response.getVideoId() + "\n");
//            jsonObject.put("VideoId",response.getVideoId());
//            logger.info("ErrorCode=" + response.getCode() + "\n");
//            jsonObject.put("ErrorCode",response.getCode());
//            logger.info("ErrorMessage=" + response.getMessage() + "\n");
//            jsonObject.put("ErrorMessage",response.getMessage());
//        }
//        return jsonObject;
//    }
//
//}
