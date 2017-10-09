package com.yn.vo;

import com.yn.domain.QueryVo;

public class ProvinceVo extends QueryVo{
	
    protected Long id;
	private String provinceText;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProvinceText() {
		return provinceText;
	}
	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}
}
