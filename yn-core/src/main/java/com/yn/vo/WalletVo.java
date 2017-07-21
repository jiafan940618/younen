package com.yn.vo;

/**
 * 钱包
 */
public class WalletVo {
	
	private Long id;
	private Long userId;
	private Double money;
	private Double integral;
	
	
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
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		this.integral = integral;
	}
}
