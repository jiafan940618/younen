package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;

@Entity
public class Brand extends IDomain implements Serializable {
	@Column(columnDefinition = "varchar(255) comment '[产品类型]'")
	private String type;
	@Column(columnDefinition = "varchar(255) comment '[品牌名]'")
	private String brandName;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
