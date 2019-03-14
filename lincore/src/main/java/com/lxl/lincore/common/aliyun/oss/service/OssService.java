package com.lxl.lincore.common.aliyun.oss.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.lxl.lincore.common.aliyun.oss.domain.dto.SignInfo;
import com.lxl.lincore.common.aliyun.oss.domain.dto.UploadInfo;
import com.lxl.common.exception.BusinessException;
import com.lxl.common.helper.ServiceConfigHelper;
import com.lxl.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.UUID;

@Service
public class OssService {

	private Logger logger = LoggerFactory.getLogger(OssService.class);

	private static String accessId;
	private static String accessKey;
	private static String bucket;
	private static String endpoint;
	//oss文件夹名称
	private static String firstSpaceName;
	//超时时长
	private static String expireTime;

	static {
		accessId = ServiceConfigHelper.getServiceConfigValue("aliyun.oss.accessId");
		accessKey = ServiceConfigHelper.getServiceConfigValue("aliyun.oss.accessKey");
		bucket = ServiceConfigHelper.getServiceConfigValue("aliyun.oss.bucket");
		endpoint = ServiceConfigHelper.getServiceConfigValue("aliyun.oss.endpoint");
		//oss文件夹名称
		firstSpaceName = ServiceConfigHelper.getServiceConfigValue("aliyun.oss.firstSpaceName");
		//超时时长
		expireTime = ServiceConfigHelper.getServiceConfigValue("aliyun.oss.expireTime");
		//检查配置文件基础参数
		checkConfigParam();
	}

	/**
	 * @Description 获取签名信息
	 * @author linxili
	 * @param param
	 * @return SignInfo
	 * @time:2018年6月21日
	 */
	public SignInfo getSignInfo(SignInfo param) { 
		try {
			// 创建OSSClient实例。
			DefaultCredentialProvider credentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(accessId, accessKey);
			OSSClient client = new OSSClient(endpoint, credentialProvider, null);

			//生成唯一文件名(oss重名会覆盖)
			String fileName = this.createUUID() + "." + param.getSuffix();
			String dir = this.getDir(fileName, param);
			String postPolicy = this.getPostPolicy(client, dir);
			String encodedPolicy = this.getEncodedPolicy(postPolicy);
			String postSignature = client.calculatePostSignature(postPolicy);
			String expire = this.getExpire();
			String host = "http://" + bucket + "." + endpoint;
			String expectPicUrl = host + "/" + dir;

			SignInfo signInfo = new SignInfo();
			signInfo.setAccessid(accessId);
			signInfo.setBucket(bucket);
			signInfo.setEndpoint(endpoint);
			signInfo.setPolicy(encodedPolicy);
			signInfo.setSignature(postSignature);
			signInfo.setFileName(fileName);
			signInfo.setDir(dir);
			signInfo.setHost(host);
			signInfo.setExpire(expire);
			signInfo.setExpectPicUrl(expectPicUrl);
			logger.info("signInfo:" + JSON.toJSONString(signInfo));
			return signInfo;
		} catch (Exception e) {
			throw new BusinessException("-2", "====签名异常====");
		}
	}

	/**
	 * @Description 文件上传
	 * @author linxili
	 * @param file
	 * @return String
	 * @time:2018年6月22日
	 */
	public String upload(UploadInfo uploadInfo) throws IOException {
		byte[] bytes = uploadInfo.getBytes();
		// 创建OSSClient实例。
		DefaultCredentialProvider credentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(accessId, accessKey);
		OSSClient ossClient = new OSSClient(endpoint, credentialProvider, null);

		//生成唯一文件名(oss重名会覆盖)
		String fileName = this.createUUID() + "." + uploadInfo.getSuffix();
		String dir = this.getDir(fileName, null);
		// 上传Byte数组。
		ossClient.putObject(bucket, dir, new ByteArrayInputStream(bytes));
		// 关闭OSSClient。
		ossClient.shutdown();

		String host = "http://" + bucket + "." + endpoint;
		String expectPicUrl = host + "/" + dir;

		logger.info("=====expectPicUrl=====" + expectPicUrl);
		return expectPicUrl;
	}
	
	/**
	 * @Description 获取预存 OSS上 文件完整路径
	 * @author linxili
	 * @param fileName
	 * @param param
	 * @return String
	 * @time:2018年6月22日
	 */
	private String getDir(String fileName, SignInfo param) {
		if (param == null && StringUtils.isEmptyStr(firstSpaceName)) {
			return fileName;
		}
		//第一级目录
		StringBuilder fileFullName = new StringBuilder();
		if (StringUtils.isNotEmpty(firstSpaceName)) {
			fileFullName = fileFullName.append(firstSpaceName);
		}
		//拼接前端传来的目录层级
		if(null != param) {
			String spaceName = param.getSpaceName();
			if (StringUtils.isNotEmpty(spaceName)) {
				if (spaceName.startsWith("/")) {
					spaceName = spaceName.substring(1);
				}
				if (spaceName.endsWith("/")) {
					spaceName = spaceName.substring(0, spaceName.length() - 1);
				}
				fileFullName.append("/").append(spaceName);
			}
		}
		if (fileFullName.length() < 1) {
			return fileName;
		}
		fileFullName.append("/").append(fileName);
		return fileFullName.toString();
	}

	private String getPostPolicy(OSSClient client, String dir) {
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

		long expireEndTime = this.getExpireEndTime();
		Date expiration = new Date(expireEndTime);
		String postPolicy = client.generatePostPolicy(expiration, policyConds);

		return postPolicy;
	}

	private long getExpireEndTime() {
		long expireTimeLong = Long.parseLong(expireTime);
		long expireEndTime = System.currentTimeMillis() + expireTimeLong * 1000;
		return expireEndTime;
	}

	private String getEncodedPolicy(String postPolicy) throws UnsupportedEncodingException {
		byte[] binaryData = postPolicy.getBytes("utf-8");
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);

		return encodedPolicy;
	}

	private String getExpire() {
		long expireEndTime = this.getExpireEndTime();
		return String.valueOf(expireEndTime / 1000);
	}

	/**
	 * @Description 检查阿里云配置文件oss基础参数
	 * @author linxili
	 * @time:2018年6月19日
	 */
	private static void checkConfigParam() {
		if (StringUtils.isEmptyStr(accessId)) {
			throw new BusinessException("-1", "缺少阿里OSS上传配置 accessId");
		}
		if (StringUtils.isEmptyStr(accessKey)) {
			throw new BusinessException("-1", "缺少阿里OSS上传配置 accessKey");
		}
		if (StringUtils.isEmptyStr(bucket)) {
			throw new BusinessException("-1", "缺少阿里OSS上传配置 bucket");
		}
		if (StringUtils.isEmptyStr(endpoint)) {
			throw new BusinessException("-1", "缺少阿里OSS上传配置 endpoint");
		}
		if (StringUtils.isEmptyStr(expireTime)) {
			throw new BusinessException("-1", "缺少阿里OSS上传配置 expireTime");
		}
	}

	/**
	 * @Description 创建32位uuid
	 * @author linxili
	 * @return String
	 * @time:2018年6月19日
	 */
	private String createUUID() {
		UUID randomUUID = UUID.randomUUID();
		String replace = randomUUID.toString().replace("-", "");
		return replace;
	}

}
