package com.yn.model;

import java.io.Serializable;

import javax.persistence.Entity;

import com.yn.domain.IDomain;

@Entity
public class Recharge  extends IDomain implements Serializable {
	/** 金额*/
	private Double money;
	/** 合计*/
	private Double total;
	/** [状态 0:成功 1:失败]*/
	private Integer status;
	/** 订单编号*/
	private String rechargeCode;
	/** [支付方式]{1:微信,2:支付宝,3:银联,4:快付通}*/
	private Integer payWay;
	
	private Long walltId;
	
	private String remark;
	
	
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getWalltId() {
		return walltId;
	}
	public void setWalltId(Long walltId) {
		this.walltId = walltId;
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
