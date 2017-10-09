package com.yn.vo;

import com.yn.domain.QueryVo;

public class CityVo extends QueryVo{
	
    protected Long id;
	private String cityText;
	private Long provinceId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
