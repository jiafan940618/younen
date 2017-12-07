package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;

/**
 * 订单交易记录和订单金额录入
 */
@Entity
public class BillOrder extends IDomain implements Serializable{

	private static final long serialVersionUID = 8510442621915013786L;
	
	@Column(columnDefinition = "int(11) comment '[订单id]'")
    private Long orderId;
	@Column(columnDefinition = "int(11) comment '[用户id]'")
    private Long userId;
	@Column(columnDefinition = "int(11) comment '[操作人id]'")
    private Long dutyUserId;
	@Column(columnDefinition = "varchar(255) comment '[交易单号]'")
	private String tradeNo;
	@Column(columnDefinition = "int(1) default 0 comment '[支付方式]{0:手动录入,1:余额支付,2:微信,3:支付宝,4:银联,5:快付通}'")
	private Integer payWay;
	@Column(precision = 12, scale = 2,columnDefinition = "decimal(12,2) comment '[金额]'")
    private Double money;
	@Column(columnDefinition = "varchar(255) comment '[备注]'")
    private String remark;
	@Column(columnDefinition = "int(1) default 0 comment '[交易状态]{0:成功,1:失败}'")
	private Integer status;

	private Long serverId;

	/**
	 * 用户
	 */
//	@ManyToOne
//    @JoinColumn(name = "userId", insertable = false, updatable = false)
//	@JsonIgnoreProperties(value = {"password","role"})
//    private User user;
	
	/**
	 * 订单
	 */
//	@ManyToOne
//    @JoinColumn(name = "orderId", insertable = false, updatable = false)
//	@JsonIgnoreProperties(value = {"billOrder"})
//    private Order order;


	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
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
