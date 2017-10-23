package com.yn.vo;

import java.io.Serializable;

import com.yn.domain.IDomain;

public class TransactionRecordVo extends IDomain implements Serializable{
	
	private Long id;
	
	private Integer type; /** 1、余额支付 2、微信 3、支付宝  4、银联  5 快付通   9、全部*/
	
	private Integer payWay;/** 1、充值 2、支付  3、提现 9、全部 */
	
	private Double money;
	
	private String remark; 
	
	private Long userId;
	
	private Integer status; /** 0、成功  1、失败   9、全部 */
	
	private String orderNo;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	



}
