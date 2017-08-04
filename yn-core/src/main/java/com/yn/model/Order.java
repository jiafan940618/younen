package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 订单
 */
@Entity
@Table(name="t_order")
public class Order extends IDomain implements Serializable {

	@Column(columnDefinition = "varchar(255) NOT NULL comment '[订单号]'")
	private String orderCode;
	@Column(columnDefinition = "int(11) NOT NULL comment '[服务商id]'")
	private Long serverId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[服务商名称]'")
	private String serverName;
	@Column(columnDefinition = "int(11) NOT NULL comment '[用户id]'")
	private Long userId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[联系人]'")
	private String linkMan; 
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[联系人电话]'")
	private String linkPhone;  
	@Column(columnDefinition = "int(11) NOT NULL comment '[省id]'")
	private Long provinceId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[省名称]'")
	private String provinceText;
	@Column(columnDefinition = "int(11) NOT NULL comment '[市id]'")
	private Long cityId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[市名称]'")
	private String cityText;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[建站地址]'")
	private String addressText;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[优惠码]'")
	private String privilegeCode;
	@Column(columnDefinition = "int(1) NOT NULL comment '[类型]{0:居民,1:工业,2:商业,3:农业}'")
	private Integer type;

    @Column(columnDefinition = "int(11) comment '[订单方案id]'")
	private Long orderPlanId;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[装机容量]'")
	private Double capacity; 
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[服务商方案价格]'")
	private Double planPrice;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[服务商的选配项目价格]'")
	private Double serverApolegamyPrice;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[优能的选配项目价格]'")
    private Double ynApolegamyPrice;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[优能服务费]'")
	private Double factoragePrice;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[总价格]'")
    private Double totalPrice;
	@Column(insertable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[已付金额]'")
	private Double hadPayPrice;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[贷款状态]{0:未申请,1:申请中,2:申请成功,3:申请失败}'")
	private Integer loanStatus;
	
	
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[订单状态]{0:申请中,1:施工中,2:并网发电申请中,3:并网发电}'")
	private Integer status;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[申请中-支付状态]{0:未支付,1:已支付}'")	
	private Integer applyIsPay;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[申请中-屋顶勘察状态]{0:未预约,1:已预约,2:勘察完成}'")	
	private Integer applyStepA;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[申请中-报建状态]{0:未申请,1:已申请,2:申请完成}'")	
	private Integer applyStepB;
	@Column(columnDefinition = "varchar(255) comment '[申请中-申请报建的图片地址]'")
	private Integer applyStepBImgUrl;
	
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[施工中-支付状态]{0:未支付,1:已支付}'")	
	private Integer buildIsPay;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[施工中-施工申请状态]{0:未申请,1:已申请}'")	
	private Integer buildStepA;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[施工中-施工状态]{0:未开始,1:材料进场,2:基础建筑,3:支架安装,4:光伏板安装,5:直流接线,6:电箱逆变器,7:汇流箱安装,8:交流辅线,9:防雷接地测试,10:并网验收}'")	
	private Integer buildStepB;
	
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[并网发电-支付状态]{0:未支付,1:已支付}'")
	private Integer gridConnectedIsPay;
	@Column(insertable=false, columnDefinition = "int(1) default 1 comment '[并网发电-并网状态]{1:申请中,2:并网完成}'")
	private Integer gridConnectedStepA;
	
	/**
	 * 用户
	 */
	@ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"password","role"})
    private User user;
	
	/**
	 * 服务商
	 */
	@ManyToOne
    @JoinColumn(name = "serverId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"user","qualifications","serverPlan","apolegamyServer"})
    private Server server;
	
	/**
	 * 订单的选配项目
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="orderId", insertable=false, updatable=true)
    private Set<ApolegamyOrder> apolegamyOrder;
	
	/**
	 * 订单的方案
	 */
    @OneToOne
    @JoinColumn(name="orderPlanId", insertable = false, updatable = false)
    private OrderPlan orderPlan;
	
	/**
	 * 订单的交易记录
	 */
	@OneToMany
    @JoinColumn(name="orderId", insertable=false, updatable=false)
    private Set<BillOrder> billOrder;

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

    public Double getServerApolegamyPrice() {
        return serverApolegamyPrice;
    }

    public void setServerApolegamyPrice(Double serverApolegamyPrice) {
        this.serverApolegamyPrice = serverApolegamyPrice;
    }

    public Double getYnApolegamyPrice() {
        return ynApolegamyPrice;
    }

    public void setYnApolegamyPrice(Double ynApolegamyPrice) {
        this.ynApolegamyPrice = ynApolegamyPrice;
    }

    public Double getFactoragePrice() {
        return factoragePrice;
    }

    public void setFactoragePrice(Double factoragePrice) {
        this.factoragePrice = factoragePrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Set<ApolegamyOrder> getApolegamyOrder() {
        return apolegamyOrder;
    }

    public void setApolegamyOrder(Set<ApolegamyOrder> apolegamyOrder) {
        this.apolegamyOrder = apolegamyOrder;
    }

    public OrderPlan getOrderPlan() {
        return orderPlan;
    }

    public void setOrderPlan(OrderPlan orderPlan) {
        this.orderPlan = orderPlan;
    }

    public Set<BillOrder> getBillOrder() {
        return billOrder;
    }

    public void setBillOrder(Set<BillOrder> billOrder) {
        this.billOrder = billOrder;
    }

    public Long getOrderPlanId() {
        return orderPlanId;
    }

    public void setOrderPlanId(Long orderPlanId) {
        this.orderPlanId = orderPlanId;
    }
}
