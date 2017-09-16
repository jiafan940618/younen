package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yn.domain.IDomain;

@Entity
@Table(name="upload_photo")
public class UploadPhoto extends IDomain implements Serializable{
	
	
	@Column(columnDefinition = "VARCHAR(255) comment '[上传图片]'")
	private String loadImg;
	@Column(columnDefinition = "int(10) comment '[服务商id]'")
	private Long serverId;
	@Column(columnDefinition = "int(10) comment '[用户id]'")
	private Long userId;
	
	
	public String getLoadImg() {
		return loadImg;
	}
	public void setLoadImg(String loadImg) {
		this.loadImg = loadImg;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
}
