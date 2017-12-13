package com.yn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.yn.domain.IDomain;

/**
 * 交易记录表 
 * */
@Entity
@Table(name="transaction_record")
public class TransactionRecord extends IDomain implements Serializable{
	
	
	private Integer type; /** 1、充值 2、支付  3、提现  4、退款   9、全部 */
	
	private Integer payWay;/** 1、余额支付 2、微信 3、支付宝  4、银联  5 快付通   9、全部*/
	
	private Double money;
	
	private String remark; 
	
	private Long userId;
	
	private Integer status; /**[状态]{0:申请中,1:退款成功,2:退款失败,9:全部} */
	
	private String orderNo;

	private Long serverId;

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
	
}
