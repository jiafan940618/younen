package com.yn.vo;

import javax.validation.constraints.NotNull;

import com.yn.domain.QueryVo;

/**
 * 选配项目
 */
public class ApolegamyVo extends QueryVo{
	
	private Long id;
	@NotNull(message = "apolegamyName不能为空")
	private String apolegamyName;
	@NotNull(message = "price不能为空")
	private Double price;
	@NotNull(message = "imgUrl不能为空")
	private String imgUrl;
	@NotNull(message = "iconUrl不能为空")
	private String iconUrl;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
