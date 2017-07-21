package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 选配项目
 */
@Entity
public class Apolegamy extends IDomain implements Serializable {
	
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[项目名称]'")
	private String apolegamyName;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[价格 元]'")
	private Double price;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[展示图片地址]'")
	private String imgUrl;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[icon图片地址]'")
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
}
