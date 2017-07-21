package com.yn.vo;

/**
 * 银行卡
 */
public class BankCardVo {
	
	private Long id;
	private Long userId;
	private String realName;
	private String idCardNum;
	private String bankName;
	private String bankCardNum;
	private String phone;
	private String accountName;
	private Integer type;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
