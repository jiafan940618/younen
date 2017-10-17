package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 提现
 */
@Entity
public class BillWithdrawals extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) comment '[用户id]'")
	private Long userId;
	@Column(columnDefinition = "int(11) comment '[钱包id]'")
	private Long walletId;
	@Column(columnDefinition = "varchar(255) comment '[交易单号]'")
	private String tradeNo;
	@Column(columnDefinition = "varchar(255) comment '[持卡人]'")
	private String realName;
	@Column(columnDefinition = "varchar(255) comment '[预留手机号]'")
	private String phone;
	@Column(columnDefinition = "varchar(255) comment '[开户银行]'")
	private String bankName;
	@Column(columnDefinition = "varchar(255) comment '[银行卡号]'")
	private String bankCardNum;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[提现金额]'")
	private Double money;
	@Column(columnDefinition = "datetime default CURRENT_TIMESTAMP comment '[申请时间]'")
    private Date applyDtm;
	@Column(columnDefinition = "int(1) default 0 comment '[提现状态]{0:申请中,1:提现成功,2:提现失败}'")
	private Integer status;
	
	private String remark;
	
	
	/**
	 * 用户
	 */
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"password","role"})
	private User user;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getWalletId() {
		return walletId;
	}
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getApplyDtm() {
		return applyDtm;
	}
	public void setApplyDtm(Date applyDtm) {
		this.applyDtm = applyDtm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
