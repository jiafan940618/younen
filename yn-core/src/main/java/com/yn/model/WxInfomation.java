package com.yn.model;

import java.io.Serializable;

import javax.persistence.Entity;

import com.yn.domain.IDomain;

@Entity
public class WxInfomation extends IDomain implements Serializable {
	
	private String userName;
	
	private String phone;
	
	private String memo;
	
	private String address;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
