package com.yn.vo;

import java.util.Set;

import com.yn.domain.QueryVo;

/**
 * 订单
 */
public class OrderVo extends QueryVo{

	private Long id;
	private String orderCode;
	private Long serverId;
	private String serverName;
	private Long userId;
	private String linkMan; 
	private String linkPhone;  
	private Long provinceId;
	private String provinceText;
	private Long cityId;
	private String cityText;
	private String addressText;
	private String privilegeCode;
	private Integer type;
	private String tradeNo;
	
	private Long orderPlanId;
	private Double capacity; 
	private Double planPrice;
	private Double apolegamyPrice;
	private Double totalPrice;
	private Double factoragePrice;
	private Double hadPayPrice;
	private Integer loanStatus;
	
	private Double speed;
	
	private Integer status;
	private Integer applyIsPay;
	private Integer applyStepA;
	private Integer applyStepB;
	private Integer applyStepBImgUrl;
	
	private Integer buildIsPay;
	private Integer buildStepA;
	private Integer buildStepB;
	
	private Integer gridConnectedIsPay;
	private Integer gridConnectedStepA;
	private Integer waPeriod ;
	
	private Set<ApolegamyOrderVo> apolegamyOrder;
	private OrderPlanVo orderPlan;
	
	
	
	public Integer getWaPeriod() {
		return waPeriod;
	}
	public void setWaPeriod(Integer waPeriod) {
		this.waPeriod = waPeriod;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceText() {
		return provinceText;
	}
	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityText() {
		return cityText;
	}
	public void setCityText(String cityText) {
		this.cityText = cityText;
	}
	public String getAddressText() {
		return addressText;
	}
	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getCapacity() {
		return capacity;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	public Double getPlanPrice() {
		return planPrice;
	}
	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}
	public Double getApolegamyPrice() {
		return apolegamyPrice;
	}
	public void setApolegamyPrice(Double apolegamyPrice) {
		this.apolegamyPrice = apolegamyPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getFactoragePrice() {
		return factoragePrice;
	}
	public void setFactoragePrice(Double factoragePrice) {
		this.factoragePrice = factoragePrice;
	}
	public Double getHadPayPrice() {
		return hadPayPrice;
	}
	public void setHadPayPrice(Double hadPayPrice) {
		this.hadPayPrice = hadPayPrice;
	}
	public Integer getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getApplyIsPay() {
		return applyIsPay;
	}
	public void setApplyIsPay(Integer applyIsPay) {
		this.applyIsPay = applyIsPay;
	}
	public Integer getApplyStepA() {
		return applyStepA;
	}
	public void setApplyStepA(Integer applyStepA) {
		this.applyStepA = applyStepA;
	}
	public Integer getApplyStepB() {
		return applyStepB;
	}
	public void setApplyStepB(Integer applyStepB) {
		this.applyStepB = applyStepB;
	}
	public Integer getApplyStepBImgUrl() {
		return applyStepBImgUrl;
	}
	public void setApplyStepBImgUrl(Integer applyStepBImgUrl) {
		this.applyStepBImgUrl = applyStepBImgUrl;
	}
	public Integer getBuildIsPay() {
		return buildIsPay;
	}
	public void setBuildIsPay(Integer buildIsPay) {
		this.buildIsPay = buildIsPay;
	}
	public Integer getBuildStepA() {
		return buildStepA;
	}
	public void setBuildStepA(Integer buildStepA) {
		this.buildStepA = buildStepA;
	}
	public Integer getBuildStepB() {
		return buildStepB;
	}
	public void setBuildStepB(Integer buildStepB) {
		this.buildStepB = buildStepB;
	}
	public Integer getGridConnectedIsPay() {
		return gridConnectedIsPay;
	}
	public void setGridConnectedIsPay(Integer gridConnectedIsPay) {
		this.gridConnectedIsPay = gridConnectedIsPay;
	}
	public Integer getGridConnectedStepA() {
		return gridConnectedStepA;
	}
	public void setGridConnectedStepA(Integer gridConnectedStepA) {
		this.gridConnectedStepA = gridConnectedStepA;
	}
	public Set<ApolegamyOrderVo> getApolegamyOrder() {
		return apolegamyOrder;
	}
	public void setApolegamyOrder(Set<ApolegamyOrderVo> apolegamyOrder) {
		this.apolegamyOrder = apolegamyOrder;
	}
	public OrderPlanVo getOrderPlan() {
		return orderPlan;
	}
	public void setOrderPlan(OrderPlanVo orderPlan) {
		this.orderPlan = orderPlan;
	}
	public Long getOrderPlanId() {
		return orderPlanId;
	}
	public void setOrderPlanId(Long orderPlanId) {
		this.orderPlanId = orderPlanId;
	}
}
