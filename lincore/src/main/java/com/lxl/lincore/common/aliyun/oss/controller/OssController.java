package com.lxl.lincore.common.aliyun.oss.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.common.aliyun.oss.domain.dto.SignInfo;
import com.lxl.lincore.common.aliyun.oss.domain.dto.UploadInfo;
import com.lxl.lincore.common.aliyun.oss.service.OssService;
import com.lxl.common.base.ResponseResult;
import com.lxl.common.helper.ValidateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/aliyun/oss")
public class OssController {

	@Autowired
	private OssService ossService;
	
	/**
	 * @Description 获取签名信息
	 * @author linxili
	 * @return ResponseResult<SignInfo>
	 * @time:2018年6月19日
	 */
	@RequestMapping(value="getSignInfo",method=RequestMethod.POST)
	public ResponseResult<SignInfo> getSignInfo(@RequestBody SignInfo param) {
		
		ValidateHelper.validateNull(param, new String[]{"suffix"});
		
		SignInfo signInfo = ossService.getSignInfo(param);
		
		return new ResponseResult<SignInfo>(signInfo);
	}
	
	/**
	 * 文件上传
	 * @param creditReport
	 * @throws IOException 
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public ResponseResult<JSONObject> upload(@RequestBody UploadInfo uploadInfo) throws IOException{
		
		ValidateHelper.validateNull(uploadInfo, new String[]{"bytes", "suffix"});
		
		String expectPicUrl = ossService.upload(uploadInfo);
		
		JSONObject json = new JSONObject();
		json.put("expectPicUrl", expectPicUrl);
		
		return new ResponseResult<JSONObject>(json);
	}
	
}
