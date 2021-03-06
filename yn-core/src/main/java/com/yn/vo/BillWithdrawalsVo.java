package com.yn.vo;

import java.util.Date;

import com.yn.domain.QueryVo;

/**
 * 提现
 */
public class BillWithdrawalsVo extends QueryVo{
	
	private Long id;
	private Long userId;
	private Long walletId;
	private String tradeNo;
	private String realName;
	private String phone;
	private String bankName;
	private String bankCardNum;
	private Double money;
    private Date applyDtm;
	private Integer status;
	
	private String treatyId;
	
	private String bankNo;
	
	
	
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getTreatyId() {
		return treatyId;
	}
	public void setTreatyId(String treatyId) {
		this.treatyId = treatyId;
	}
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
	public Long getWalletId() {
		return walletId;
	}
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCardNum() {
		return bankCardNum;
	}
	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getApplyDtm() {
		return applyDtm;
	}
	public void setApplyDtm(Date applyDtm) {
		this.applyDtm = applyDtm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
