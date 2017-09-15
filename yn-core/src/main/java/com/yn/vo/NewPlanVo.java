package com.yn.vo;

import java.math.BigDecimal;

public class NewPlanVo {
	
	
	
	private Integer id; 
	private Integer serverId;
	private String materialJson;
	private Integer minPurchase;
	private BigDecimal unitPrice;
	private String img_url;
	
	private String invstername; 
	private String brandname;
	
	private Double allMoney;
	
	private Double apoPrice;
	
	private Double serPrice;
	
	private String conent;
	
	private Integer capacity;
	
	private Integer num;
	
	private Integer warPeriod;
	
	private Integer status;

	//订单服务商与用户的信息
	private String companyName;
	private String orderCode;
	private String phone;
	private String address;
	private String userName;
	
	private String ids;
	
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getWarPeriod() {
		return warPeriod;
	}
	public void setWarPeriod(Integer warPeriod) {
		this.warPeriod = warPeriod;
	}
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getApoPrice() {
		return apoPrice;
	}
	public void setApoPrice(Double apoPrice) {
		this.apoPrice = apoPrice;
	}
	public Double getSerPrice() {
		return serPrice;
	}
	public void setSerPrice(Double serPrice) {
		this.serPrice = serPrice;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public String getConent() {
		return conent;
	}
	public void setConent(String conent) {
		this.conent = conent;
	}
	public Double getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public String getMaterialJson() {
		return materialJson;
	}
	public void setMaterialJson(String materialJson) {
		this.materialJson = materialJson;
	}
	public Integer getMinPurchase() {
		return minPurchase;
	}
	public void setMinPurchase(Integer minPurchase) {
		this.minPurchase = minPurchase;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getInvstername() {
		return invstername;
	}
	public void setInvstername(String invstername) {
		this.invstername = invstername;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	

}
