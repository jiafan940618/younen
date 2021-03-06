package com.yn.vo;

import java.math.BigDecimal;

import com.yn.domain.QueryVo;

/**
 * 订单交易记录和订单金额录入
 */
public class BillOrderVo extends QueryVo{
	
	private Long id;
    private Long dutyUserId;
    private String remark;
	private Integer status;
	/** 余额*/
	private BigDecimal balancePrice;
	
	private Long orderId;
	private Long userId;      //用户id
    private BigDecimal money;  //交易的金额
	private String channel;     //微信与支付宝方式的代码
	private Integer payWay;    //那种支付方式
	
	
	private String tradeNo;     //订单号
	
	private String description;   
	
	private String key;
	
	private String requestUrl;
	
	private String mchId;
	
	private String boby;
	
	private String subject;
	
	private String bankType;

	private Long serverId;

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBoby() {
		return boby;
	}
	public void setBoby(String boby) {
		this.boby = boby;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
