package com.lxl.lincore.common.aliyun.video.controller;
//package com.wsh.common.aliyun.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.wsh.common.aliyun.domain.dto.UploadVideoInfo;
//import com.wsh.common.aliyun.service.UploadVideoService;
//import com.wsh.common.base.ResponseResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author linxilii
// * @Description:
// * @Date: 2018/9/12 10:56
// */
//@RestController
//@RequestMapping("/aliyun/uploadVideo")
//public class UploadVideoController {
//
//    @Autowired
//    private UploadVideoService uploadVideoService;
//
//    /**
//     * @Author linxilii
//     * @Description: 本地文件上传接口
//     * @Date: 2018/9/12
//     * @param param
//     * @return: com.wsh.common.base.ResponseResult<com.alibaba.fastjson.JSONObject>
//     **/
//    @RequestMapping(value="uploadVideo",method=RequestMethod.POST)
//    public ResponseResult<JSONObject> uploadVideo(@RequestBody UploadVideoInfo param) {
//
//        JSONObject jsonObject = uploadVideoService.uploadVideo(param);
//
//        return new ResponseResult<JSONObject>(jsonObject);
//    }
//
//    /**
//     * @Author linxilii
//     * @Description: 本地文件上传接口
//     * @Date: 2018/9/12
//     * @param param
//     * @return: com.wsh.common.base.ResponseResult<com.alibaba.fastjson.JSONObject>
//     **/
//    @RequestMapping(value="uploadURLStream",method=RequestMethod.POST)
//    public ResponseResult<JSONObject> uploadURLStream(@RequestBody UploadVideoInfo param) {
//
//        JSONObject jsonObject = uploadVideoService.uploadURLStream(param);
//
//        return new ResponseResult<JSONObject>(jsonObject);
//    }
//
//    /**
//     * @Author linxilii
//     * @Description: 文件流上传接口
//     * @Date: 2018/9/12
//     * @param param
//     * @return: com.wsh.common.base.ResponseResult<com.alibaba.fastjson.JSONObject>
//     **/
//    @RequestMapping(value="uploadFileStream",method=RequestMethod.POST)
//    public ResponseResult<JSONObject> uploadFileStream(@RequestBody UploadVideoInfo param) {
//
//        JSONObject jsonObject = uploadVideoService.uploadFileStream(param);
//
//        return new ResponseResult<JSONObject>(jsonObject);
//    }
//
//    /**
//     * @Author linxilii
//     * @Description: 流式上传接口
//     * @Date: 2018/9/12
//     * @param param
//     * @return: com.wsh.common.base.ResponseResult<com.alibaba.fastjson.JSONObject>
//     **/
//    @RequestMapping(value="uploadStream",method=RequestMethod.POST)
//    public ResponseResult<JSONObject> uploadStream(@RequestBody UploadVideoInfo param) {
//
//        JSONObject jsonObject = uploadVideoService.uploadStream(param);
//
//        return new ResponseResult<JSONObject>(jsonObject);
//    }
//
//
//
//}
