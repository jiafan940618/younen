package com.yn.vo;

/**
 * 服务商承诺
 */
public class QualificationsVo {
	
	protected Long id;
	private String imgUrl;
	private String text;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
}
