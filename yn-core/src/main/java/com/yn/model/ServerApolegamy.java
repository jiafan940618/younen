package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 服务商选配项目
 */
@Entity
public class ServerApolegamy extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) comment '[服务商id]'")
	private Long serverId;
	@Column(columnDefinition = "varchar(255) comment '[项目名称]'")
	private String apolegamyName;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[价格 元]'")
	private Double price;
	@Column(columnDefinition = "varchar(255) comment '[展示图片地址]'")
	private String imgUrl;
	@Column(columnDefinition = "varchar(255) comment '[icon图片地址]'")
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
