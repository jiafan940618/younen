package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AmmeterStatusCode extends IDomain implements Serializable {

	@Column(columnDefinition = "varchar(255) comment '[电表状态码]'")
	private String statusCode;
	@Column(columnDefinition = "varchar(255) comment '[状态码说明]'")
	private String statusCodeDesc;
	@Column(insertable=false, columnDefinition = "int(1) comment '[是否正常]{0:正常,1:不正常}'")
	private Integer isNormal;
	
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Integer getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(Integer isNormal) {
		this.isNormal = isNormal;
	}
	public String getStatusCodeDesc() {
		return statusCodeDesc;
	}
	public void setStatusCodeDesc(String statusCodeDesc) {
		this.statusCodeDesc = statusCodeDesc;
	}
}
