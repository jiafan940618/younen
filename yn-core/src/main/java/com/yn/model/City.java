package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * 市
 *
 */
@Entity
public class City extends IDomain implements Serializable{
	
	@Column(columnDefinition = "varchar(50) comment '[城市名]'")
	private String cityText;
	@Column(columnDefinition = "int(11) comment '[所属省id]'")
	private Long provinceId;
	
	
	public String getCityText() {
		return cityText;
	}
	public void setCityText(String cityText) {
		this.cityText = cityText;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
}
