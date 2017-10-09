package com.yn.vo;

import com.yn.domain.QueryVo;

public class SystemConfigVo extends QueryVo{
	
    protected Long id;
    protected String propertyKey;
    protected String propertyValue;
    protected String remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPropertyKey() {
		return propertyKey;
	}
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
