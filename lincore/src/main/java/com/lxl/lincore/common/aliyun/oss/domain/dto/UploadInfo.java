package com.lxl.lincore.common.aliyun.oss.domain.dto;

public class UploadInfo {

	/** 文件字节数组 */
	byte[] bytes;
	
	/** 文件后缀 */
	private String suffix;
	
	/** 预计图片URL(上传成功时有效,失败时该url无用) */
	private String expectPicUrl;
	

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
	/** 文件字节数组 */
	public byte[] getBytes() {
		return bytes;
	}
	/** 文件字节数组 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
