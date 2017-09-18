package com.yn.vo;

import java.math.BigDecimal;

import com.yn.domain.QueryVo;

/**
 * 订单交易记录和订单金额录入
 */
public class BillOrderVo extends QueryVo{
	
	private Long id;
    private Long orderId;
    private Long userId;
    private Long dutyUserId;
    private String tradeNo;
	private Integer payWay;
    private BigDecimal money;
    private String remark;
	private Integer status;
	
	private String channel;
	
	/** 余额*/
	private BigDecimal balancePrice;
	
	
	
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public BigDecimal getBalancePrice() {
		return balancePrice;
	}
	public void setBalancePrice(BigDecimal balancePrice) {
		this.balancePrice = balancePrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Long getDutyUserId() {
		return dutyUserId;
	}
	public void setDutyUserId(Long dutyUserId) {
		this.dutyUserId = dutyUserId;
	}
}
