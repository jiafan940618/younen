package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 系统参数
 *
 */
@Entity
public class SystemConfig extends IDomain implements Serializable {
	
	@Column(updatable = true, columnDefinition = "varchar(50) comment '[key]'")
	private String propertyKey;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[值]'")
	private String propertyValue;
	@Column(updatable = true, columnDefinition = "varchar(50) comment '[备注]'")
	private String remark;

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
