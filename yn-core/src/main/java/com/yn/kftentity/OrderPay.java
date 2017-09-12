package com.yn.kftentity;

import java.math.BigDecimal;

/**
 * 支付订单 pojo
 * */
public class OrderPay {
	
	private int id;
	/** 提交方式*/
	private String tradeType="cs.pay.submit";
	/** 版本号*/
	private String version="1.6";
	/** 订单状态*/
	private String orderstatus;
	/** 商户id*/
	private String userid;
	/** 商品标题*/
	private String subject;
	/** 支付类型*/
	private String channel;
	/** 终端类型*/
	private String terminaltype;
	/** 商品描述*/
	private String body;
	/** 商品订单号*/
	private String outtradeno;
	/** 金额*/
	private BigDecimal amount;
	/** 附加数据*/
	private String description;
	/** 货币类型*/
	private String currency;
	/** 订单时间*/
	private String timepaid;
	/** 订单失效时间*/
	private String timeexpire;
	/** 扩展字段*/
	private String extra;
	/** 返回状态码*/
	private String returncode;
	/** 返回信息*/
	private String returnmsg;
	/** 数据签名*/
	private String sign;
	/** 业务结果   0、成功 1、失败*/
	private String resultcode;
	/** 返回二维串*/
	private String codeurl;
	/** 支付码信息*/
	private String paycode;
	/** 渠道订单号*/
	private String outchannelno;
	/** 借贷标识*/
	private String paytype;
	/** 错误代码*/
	private String errcode;
	
	private String errcodedes;
	
	private String banktype;
	
	private String customertype;
	
	
	
	

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

	public String getCustomertype() {
		return customertype;
	}

	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTerminaltype() {
		return terminaltype;
	}

	public void setTerminaltype(String terminaltype) {
		this.terminaltype = terminaltype;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOuttradeno() {
		return outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTimepaid() {
		return timepaid;
	}

	public void setTimepaid(String timepaid) {
		this.timepaid = timepaid;
	}

	public String getTimeexpire() {
		return timeexpire;
	}

	public void setTimeexpire(String timeexpire) {
		this.timeexpire = timeexpire;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getReturncode() {
		return returncode;
	}

	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}

	public String getReturnmsg() {
		return returnmsg;
	}

	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getCodeurl() {
		return codeurl;
	}

	public void setCodeurl(String codeurl) {
		this.codeurl = codeurl;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getOutchannelno() {
		return outchannelno;
	}

	public void setOutchannelno(String outchannelno) {
		this.outchannelno = outchannelno;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}


	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getErrcodedes() {
		return errcodedes;
	}

	public void setErrcodedes(String errcodedes) {
		this.errcodedes = errcodedes;
	}

	
}
