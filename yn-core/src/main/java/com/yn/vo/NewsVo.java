package com.yn.vo;

import com.yn.domain.QueryVo;

/**
 * 资讯，动态，视频
 * @author hy-03
 *
 */
public class NewsVo extends QueryVo {
	
	private Long id;
	private String title;
	private String author;
	private Integer clientId;
	private Integer browseCount;
	private String imgUrl;
	private String videoUrl;
	private String content;
	private Integer type;
	
	private String[] imgUrls;
	
	
	
	public String[] getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String[] imgUrls) {
		this.imgUrls = imgUrls;
	}
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
