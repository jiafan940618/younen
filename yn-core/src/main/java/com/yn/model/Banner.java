package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * banner
 * @author hy-03
 *
 */
@Entity
public class Banner extends IDomain implements Serializable {
	@Id
	@GeneratedValue
	@Column(columnDefinition = "int(11) comment '[id]'")
	private Long id;   
	@Column(columnDefinition = "varchar(255) comment '[图片地址]'")
	private String imgUrl;
	@Column(columnDefinition = "varchar(255) comment '[链接地址]'")
	private String linkUrl;
	@Column(columnDefinition = "int(1) comment '[是否展示]{0:展示,1:不展示}'")
	private Integer isShow;
	
	public Banner() {}
	
	
	public Banner(Long id, String imgUrl, String linkUrl) {
		this.id = id;
		this.imgUrl = imgUrl;
		this.linkUrl = linkUrl;
	}

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
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
}
