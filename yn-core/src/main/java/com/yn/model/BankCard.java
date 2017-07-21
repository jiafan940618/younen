package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 银行卡
 */
@Entity
public class BankCard extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) default 0 comment '[用户id]'")
	private Long userId;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[真实姓名]'")
	private String realName;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[身份证号]'")
	private String idCardNum;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[开户银行]'")
	private String bankName;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[银行卡号]'")
	private String bankCardNum;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[预留手机号]'")
	private String phone;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[账户名称]'")
	private String accountName;
	@Column(insertable=true, updatable=true, columnDefinition = "int(1) comment '[是否已经认证]{0:个人账户,1:工商账户}'")
	private Integer type;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCardNum() {
		return bankCardNum;
	}
	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
