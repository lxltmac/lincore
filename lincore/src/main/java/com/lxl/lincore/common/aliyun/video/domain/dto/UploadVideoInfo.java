package com.lxl.lincore.common.aliyun.video.domain.dto;
import java.io.InputStream;

public class UploadVideoInfo {
	/** 阿里视频点播 accessKeyId*/
	private String accessKeyId;

	/** 阿里视频点播 accessKeySecret */
	private String accessKeySecret;

	/** 阿里视频点播 视频上传标题 */
	private String title;

	/** 阿里视频点播  文件名*/
	private String fileName;

	/** 阿里视频点播  上传视频url*/
	private String url;

	/** 阿里视频点播  文件流*/
	private InputStream inputStream;

	/** 阿里视频点播  描述*/
	private String description;

	/** 阿里视频点播  封面图*/
	private String coverURL;

	/** 阿里视频点播  视频ID*/
	private String videoId;

	/** 阿里视频点播  获取视频地址系统规定参数*/
	private String action;

	/** 阿里视频点播  视频分类ID*/
	private Long CateId;

	/** 阿里视频点播  视频文件大小*/
	private String FileSize;

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverURL() {
		return coverURL;
	}

	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getCateId() {
		return CateId;
	}

	public void setCateId(Long cateId) {
		CateId = cateId;
	}

	public String getFileSize() {
		return FileSize;
	}

	public void setFileSize(String fileSize) {
		FileSize = fileSize;
	}
}
