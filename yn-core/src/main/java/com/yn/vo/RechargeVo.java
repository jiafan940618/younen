package com.yn.vo;

import java.math.BigDecimal;

public class RechargeVo {
	/** 金额*/
	private BigDecimal money;
	/** 合计*/
	private Double total;
	/** [状态 0:成功 1:失败]*/
	private Integer status;
	/** 订单编号*/
	private String rechargeCode;
	/** [支付方式]{1:微信,2:支付宝,3:银联,4:快付通}*/
	private Integer payWay;
	
	private Long walletId;
	
	private String bankType;
	
	private String boby;
	
	private String subject;
	
	private String description;
	
	private BigDecimal totalmoney;
	
	private Long userId;

	private Long serverId;


	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	public BigDecimal getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBoby() {
		return boby;
	}
	public void setBoby(String boby) {
		this.boby = boby;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getWalletId() {
		return walletId;
	}
	public void setWalltId(Long walletId) {
		this.walletId = walletId;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRechargeCode() {
		return rechargeCode;
	}
	public void setRechargeCode(String rechargeCode) {
		this.rechargeCode = rechargeCode;
	}
}