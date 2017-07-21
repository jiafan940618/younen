package com.yn.vo;

/**
 * 服务商资质
 */
public class ServerQualificationsVo {
	
	private Long id;
	private Long serverId;
	private String imgUrl;
	private String text;
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
}
