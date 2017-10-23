package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 电站
 */
@Entity
public class Station extends IDomain implements Serializable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) comment '[id]'")
    private Long id;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[电站名称]'")
	private String stationName;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[电站码]'")
	private String stationCode;
	@Column(columnDefinition = "int(11) NOT NULL comment '[订单id]'")
	private Long orderId;
	@Column(columnDefinition = "int(11) NOT NULL comment '[用户id]'")
	private Long userId;
	@Column(columnDefinition = "int(11) NOT NULL comment '[服务商id]'")
	private Long serverId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[联系人]'")
	private String linkMan; 
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[联系人电话]'")
	private String linkPhone;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[装机容量]'")
	private Double capacity;
	@Column(columnDefinition = "int(11) NOT NULL comment '[省id]'")
	private Long provinceId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[省名称]'")
	private String provinceText;
	@Column(columnDefinition = "int(11) NOT NULL comment '[市id]'")
	private Long cityId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[市名称]'")
	private String cityText;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[电站地址]'")
	private String addressText;
	@Column(columnDefinition = "varchar(255) comment '[采集器码]'")
	private String devConfCode;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[电站状态]{0:未绑定电表,1:正在发电,2:电表异常}'")
	private Integer status;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[通道模式]{0:合计模式,1:多通道模式}'")
	private Integer passageModel;
	@Column(columnDefinition = "int(1) NOT NULL comment '[类型]{0:居民,1:工业,2:商业,3:农业}'")
	private Integer type;


    /**
     * 是否已读
     */
    @Transient
    private Integer isRead;

	
	/**
	 * 订单
	 */
	@OneToOne
    @JoinColumn(name = "orderId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"user","server","billOrder"})
    private Order order;
	
	/**
	 * 用户
	 */
	@ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"role","bankCard"})
    private User user;
	
	/**
	 * 服务商
	 */
	@ManyToOne
    @JoinColumn(name = "serverId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"user","serverQualifications","serverPlan","apolegamyServer"})
    private Server server;
	
	/**
	 * 电表
	 */
	@OneToMany
	@JoinColumn(name = "stationId", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"station"})
	@Where(clause="del=0")
	private Set<Ammeter> ammeter;

    public Station() {}

	public String getStationName() {
        return stationName;
    }

    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
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

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
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

    public String getDevConfCode() {
        return devConfCode;
    }

    public void setDevConfCode(String devConfCode) {
        this.devConfCode = devConfCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPassageModel() {
        return passageModel;
    }

    public void setPassageModel(Integer passageModel) {
        this.passageModel = passageModel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Set<Ammeter> getAmmeter() {
        return ammeter;
    }

    public void setAmmeter(Set<Ammeter> ammeter) {
        this.ammeter = ammeter;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
