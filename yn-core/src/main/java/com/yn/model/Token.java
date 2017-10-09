package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 微信token
 *
 */
@Entity
public class Token extends IDomain implements Serializable{
	
	@Column(columnDefinition = "varchar(255) comment '[名称]'")
    private String tokenKey;
	@Column(columnDefinition = "varchar(255) comment '[值]'")
    private String tokenValue;
	@Column(columnDefinition = "varchar(255) comment '[过期时间]'")
    private String expiresTime;
	public String getTokenKey() {
		return tokenKey;
	}
	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	public String getTokenValue() {
		return tokenValue;
	}
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	public String getExpiresTime() {
		return expiresTime;
	}
	public void setExpiresTime(String expiresTime) {
		this.expiresTime = expiresTime;
	}
	
}
