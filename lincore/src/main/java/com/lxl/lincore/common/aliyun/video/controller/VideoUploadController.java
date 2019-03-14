package com.lxl.lincore.common.aliyun.video.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.common.aliyun.video.domain.dto.UploadVideoInfo;
import com.lxl.lincore.common.aliyun.video.service.VideoUploadService;
import com.lxl.common.base.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linxilii
 * @Description:
 * @Date: 2018/9/13 15:37
 */
@RestController
@RequestMapping("/aliyun/uploadVideo")
public class VideoUploadController {

    @Autowired
    private VideoUploadService uploadVideoService;

    /**
     * @Author linxilii
     * @Description: 获取视频上传信息
     * @Date: 2018/9/12
     * @param param
     * @return: com.wsh.common.base.ResponseResult<com.alibaba.fastjson.JSONObject>
     **/
    @RequestMapping(value="uploadVideoInfo",method=RequestMethod.POST)
    public ResponseResult<JSONObject> getUploadInfo(@RequestBody UploadVideoInfo param) {

        JSONObject jsonObject = uploadVideoService.getUploadInfo(param);

        return new ResponseResult<JSONObject>(jsonObject);
    }

    /**
     * @Author linxilii
     * @Description: 刷新获取视频上传信息
     * @Date: 2018/9/12
     * @param param
     * @return: com.wsh.common.base.ResponseResult<com.alibaba.fastjson.JSONObject>
     **/
    @RequestMapping(value="refreshUploadInfo",method=RequestMethod.POST)
    public ResponseResult<JSONObject> refreshUploadInfo(@RequestBody UploadVideoInfo param) {

        JSONObject jsonObject = uploadVideoService.refreshUploadInfo(param);

        return new ResponseResult<JSONObject>(jsonObject);
    }
}
