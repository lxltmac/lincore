package com.lxl.lincore.common.aliyun.oss.domain.dto;

public class SignInfo {

	/** 阿里OSS accessid*/
	private String accessid;
	
	/** 阿里OSS accessKey */
	private String accessKey;
	
	/** 阿里OSS bucket */
	private String bucket;
	
	/** 阿里OSS endpoint */
	private String endpoint;
	
	/** 字符串(用于签名) */
	private String policy;
	
	/** 签名(有时效) */
	private String signature;
	
	/** oss上的图片路径 (例:test/1.jpg) */
	private String dir;
	
	/** 文件夹名 */
	private String spaceName;
	
	/** 文件名(带后缀) */
	private String fileName;
	
	/** 文件后缀 */
	private String suffix;
	
	/** 请求地址(格式:http:// + bucket + "." + endpoint) */
	private String host;
	
	/** 失效日期 (时间戳/1000) */
	private String expire;
	
	/** 预计图片URL(上传成功时有效,失败时该url无用) */
	private String expectPicUrl;
	

	/** 阿里OSS accessid*/
	public String getAccessid() {
		return accessid;
	}
	/** 阿里OSS accessid*/
	public void setAccessid(String accessid) {
		this.accessid = accessid;
	}
	/** 阿里OSS accessKey */
	public String getAccessKey() {
		return accessKey;
	}
	/** 阿里OSS accessKey */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	/** 阿里OSS bucket */
	public String getBucket() {
		return bucket;
	}
	/** 阿里OSS bucket */
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	/** 阿里OSS endpoint */
	public String getEndpoint() {
		return endpoint;
	}
	/** 阿里OSS endpoint */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	/** 字符串(用于签名) */
	public String getPolicy() {
		return policy;
	}
	/** 字符串(用于签名) */
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	/** 签名(有时效) */
	public String getSignature() {
		return signature;
	}
	/** 签名(有时效) */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/** 文件名(带后缀) */
	public String getFileName() {
		return fileName;
	}
	/** 文件名(带后缀) */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/** oss上的图片路径 (例:test/1.jpg) */
	public String getDir() {
		return dir;
	}
	/** oss上的图片路径 (例:test/1.jpg) */
	public void setDir(String dir) {
		this.dir = dir;
	}
	/** 请求地址(格式:http:// + bucket + "." + endpoint) */
	public String getHost() {
		return host;
	}
	/** 请求地址(格式:http:// + bucket + "." + endpoint) */
	public void setHost(String host) {
		this.host = host;
	}
	/** 失效日期 (时间戳/1000) */
	public String getExpire() {
		return expire;
	}
	/** 失效日期 (时间戳/1000) */
	public void setExpire(String expire) {
		this.expire = expire;
	}
	/** 预计图片URL(上传成功时有效,失败时该url无用) */
	public String getExpectPicUrl() {
		return expectPicUrl;
	}
	/** 预计图片URL(上传成功时有效,失败时该url无用) */
	public void setExpectPicUrl(String expectPicUrl) {
		this.expectPicUrl = expectPicUrl;
	}
	/** 文件后缀 */
	public String getSuffix() {
		return suffix;
	}
	/** 文件后缀 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	/** 文件夹名 */
	public String getSpaceName() {
		return spaceName;
	}
	/** 文件夹名 */
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	
}
