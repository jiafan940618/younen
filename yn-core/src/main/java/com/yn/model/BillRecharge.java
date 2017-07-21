package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

/**
 * 充值记录
 */
@Entity
public class BillRecharge extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) comment '[用户id]'")
	private Long userId;
	@Column(columnDefinition = "varchar(255) comment '[交易单号]'")
	private String tradeNo;
	@Column(columnDefinition = "int(1) comment '[充值状态]{0:充值成功,1:充值失败}'")
	private Integer status;
	@Column(columnDefinition = "int(1) comment '[支付方式]{2:微信,3:支付宝,4:银联,5:快付通}'")
	private Integer payWay;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[充值金额]'")
	private Double money;
	
	/**
	 * 用户
	 */
	@ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"password","role"})
    private User user;
	
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
