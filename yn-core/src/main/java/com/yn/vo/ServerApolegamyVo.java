package com.yn.vo;

/**
 * 服务商选配项目
 */
public class ServerApolegamyVo {
	
	private Long serverId;
	private String apolegamyName;
	private Double price;
	private String imgUrl;
	private String iconUrl;
	
	
	public String getApolegamyName() {
		return apolegamyName;
	}
	public void setApolegamyName(String apolegamyName) {
		this.apolegamyName = apolegamyName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
}
