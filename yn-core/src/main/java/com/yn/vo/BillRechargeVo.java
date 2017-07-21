package com.yn.vo;

import com.yn.domain.QueryVo;

/**
 * 充值记录
 */
public class BillRechargeVo extends QueryVo{
	
	private Long id;
	private Long userId;
	private String tradeNo;
	private Integer status;
	private Integer payWay;
	private Double money;
	
	
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
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
}
