package com.yn.kftentity;

import java.math.BigDecimal;

/**
 * 退款 pojo
 * 
 */
public class OrderRefund {
	
	private int  ref_id; 
	/** 用户id*/
	private String userid;
	/** 提交方式*/
	private String tradeType="cs.refund.apply";
	/** 版本号*/
	private String version="1.6";
	/** 支付类型*/
	private String ref_channel;
	/** 订单号*/
	private String ref_outtradeno;
	/** 退款单号*/
	private String outrefundno;
	/** 退款金额*/
	private BigDecimal ref_amount;
	/** 退款详情*/
	private String ref_description;
	/** 前台回调url*/
	private String callbackurl;
	/** 后台通知url*/
	private String notifyurl;
	/** 返回状态码 0、成功  1、失败*/
	private String ref_returncode;
	/** 返回信息*/
	private String ref_returnmsg;
	/** 数据签名*/
	private String ref_sign;
	/** 业务结果状态*/
	private String resultcode;
	/** 渠道退款单号*/
	private String channelrefundno;
	/** 退款状态*/
	private String ref_status;
	/** 错误代码 */
	private String ref_errcode;
	/** 错误描述*/
	private String ref_errcodedes;
	
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getRef_id() {
		return ref_id;
	}
	public void setRef_id(int ref_id) {
		this.ref_id = ref_id;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
	public String getRef_channel() {
		return ref_channel;
	}
	public void setRef_channel(String ref_channel) {
		this.ref_channel = ref_channel;
	}
	public String getRef_outtradeno() {
		return ref_outtradeno;
	}
	public void setRef_outtradeno(String ref_outtradeno) {
		this.ref_outtradeno = ref_outtradeno;
	}
	public String getOutrefundno() {
		return outrefundno;
	}
	public void setOutrefundno(String outrefundno) {
		this.outrefundno = outrefundno;
	}
	public BigDecimal getRef_amount() {
		return ref_amount;
	}
	public void setRef_amount(BigDecimal ref_amount) {
		this.ref_amount = ref_amount;
	}
	public String getRef_description() {
		return ref_description;
	}
	public void setRef_description(String ref_description) {
		this.ref_description = ref_description;
	}
	public String getCallbackurl() {
		return callbackurl;
	}
	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}
	public String getNotifyurl() {
		return notifyurl;
	}
	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}
	public String getRef_returncode() {
		return ref_returncode;
	}
	public void setRef_returncode(String ref_returncode) {
		this.ref_returncode = ref_returncode;
	}
	public String getRef_returnmsg() {
		return ref_returnmsg;
	}
	public void setRef_returnmsg(String ref_returnmsg) {
		this.ref_returnmsg = ref_returnmsg;
	}
	public String getRef_sign() {
		return ref_sign;
	}
	public void setRef_sign(String ref_sign) {
		this.ref_sign = ref_sign;
	}
	public String getResultcode() {
		return resultcode;
	}
	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
	public String getChannelrefundno() {
		return channelrefundno;
	}
	public void setChannelrefundno(String channelrefundno) {
		this.channelrefundno = channelrefundno;
	}
	public String getRef_status() {
		return ref_status;
	}
	public void setRef_status(String ref_status) {
		this.ref_status = ref_status;
	}
	public String getRef_errcode() {
		return ref_errcode;
	}
	public void setRef_errcode(String ref_errcode) {
		this.ref_errcode = ref_errcode;
	}
	public String getRef_errcodedes() {
		return ref_errcodedes;
	}
	public void setRef_errcodedes(String ref_errcodedes) {
		this.ref_errcodedes = ref_errcodedes;
	}
	
}
