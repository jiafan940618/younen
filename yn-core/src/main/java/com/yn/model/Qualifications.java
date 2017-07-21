package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 资质
 */
@Entity
public class Qualifications extends IDomain implements Serializable {
	
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[icon图片地址]'")
	private String imgUrl;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[文字说明]'")
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
}
