package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * 市
 *
 */
@Entity
public class City extends IDomain implements Serializable{
	
	@Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) comment '[id]'")
    private Long id;
	@Column(columnDefinition = "varchar(50) comment '[城市名]'")
	private String cityText;
	@Column(columnDefinition = "int(11) comment '[所属省id]'")
	private Long provinceId;
	
	
	
	public City() {}
	public City(Long id, String cityText) {
		this.id = id;
		this.cityText = cityText;
	}
	public String getCityText() {
		return cityText;
	}
	public void setCityText(String cityText) {
		this.cityText = cityText;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	
	
}
