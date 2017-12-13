package com.yn.model;

import java.io.Serializable;

import javax.persistence.Entity;

import com.yn.domain.IDomain;

@Entity
public class Recharge  extends IDomain implements Serializable {
	/** 金额*/
	private Double money;
	/** [状态 0:成功 1:失败]*/
	private Integer status;
	/** 订单编号*/
	private String tradeNo;
	/** [支付方式]{2:微信,3:支付宝,4:银联,5:快付通}*/
	private Integer payWay;
	
	private Long walletId;
	
	private String remark;
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getWalletId() {
		return walletId;
	}
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
}
