package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 钱包
 */
@Entity
public class Wallet extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) comment '[用户id]'")
	private Long userId;
	@Column(insertable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[余额 元]'")
	private BigDecimal money;
	@Column(insertable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[积分 元]'")
	private Double integral;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
	}
}
