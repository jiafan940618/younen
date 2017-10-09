package com.yn.vo;

public class AmmeterStatusCodeVo {

	private Long id;
	private String statusCode;
	private String statusCodeDesc;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
