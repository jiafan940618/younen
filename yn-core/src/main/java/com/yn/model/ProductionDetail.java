package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;

@Entity
public class ProductionDetail extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(5) comment '[服务商编号]'")
	private Integer serverId;
	@Column(columnDefinition = "text comment '[内容]'")
	private String content;



	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
