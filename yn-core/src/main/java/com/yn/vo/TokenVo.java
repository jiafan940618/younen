package com.yn.vo;

import com.yn.domain.QueryVo;

public class TokenVo extends QueryVo{
	
    protected Long id;
    protected String expiresTime;
    protected String tokenKey;
    protected String tokenValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExpiresTime() {
		return expiresTime;
	}
	public void setExpiresTime(String expiresTime) {
		this.expiresTime = expiresTime;
	}
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
	
}
