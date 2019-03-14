package com.lxl.lincore.common.aliyun.video.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.common.aliyun.video.domain.dto.UploadVideoInfo;
import com.lxl.lincore.common.aliyun.video.service.SignatureService;
import com.lxl.common.base.ResponseResult;
import com.lxl.common.helper.ValidateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author linxilii
 * @Description:
 * @Date: 2018/9/14 14:25
 */
@RestController
@RequestMapping("/aliyun/signature")
public class SignatureController {

    @Autowired
    private SignatureService signatureService;

    @RequestMapping(value="getUploadVideoUrl",method=RequestMethod.POST)
    public ResponseResult<JSONObject> getUploadVideoUrl(@RequestBody UploadVideoInfo param) throws IOException {

        //参数校验
        ValidateHelper.validateNull(param, new String[]{"videoId","action"});

        JSONObject jsonObject = signatureService.getUploadVideoUrl(param);
        return new ResponseResult<JSONObject>(jsonObject);
    }

}
