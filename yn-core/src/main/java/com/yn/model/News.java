package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 资讯，动态，视频
 * @author hy-03
 *
 */
@Entity
public class News extends IDomain implements Serializable {
	
	@Column(columnDefinition = "varchar(255) comment '[标题]'")
	private String title;
	@Column(columnDefinition = "varchar(255) comment '[作者]'")
	private String author;
	@Column(columnDefinition = "int(1) comment '[适用设备]{0:手机设备,1:电脑设备}'")
	private Integer clientId;
	@Column(columnDefinition = "int(11) comment '[浏览数]'")
	private Integer browseCount;
	@Column(columnDefinition = "varchar(255) comment '[图片地址]'")
	private String imgUrl;
	@Column(columnDefinition = "varchar(255) comment '[视频地址]'")
	private String videoUrl;
	@Column(columnDefinition = "text comment '[内容]'")
	private String content;
	@Column(columnDefinition = "int(1) comment '[类型]{0:资讯,1:动态,2:视频,3:服务商公告}'")
	private Integer type;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getBrowseCount() {
		return browseCount;
	}
	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
