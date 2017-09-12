package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 服务商
 */
@Entity
public class Server extends IDomain implements Serializable {
	
	 @Id
	    @GeneratedValue
	    @Column(columnDefinition = "int(11) comment '[id]'")
	    private Long id;
    @Column(columnDefinition = "int(11) comment '[用户id]'")
    private Long userId;
    @Column(columnDefinition = "varchar(255) comment '[企业邮箱]'")
    private String companyEmail;
    @Column(columnDefinition = "varchar(255) comment '[企业logo]'")
    private String companyLogo;
    @Column(columnDefinition = "varchar(255) comment '[企业名称]'")
    private String companyName;
    @Column(columnDefinition = "varchar(255) comment '[企业地址]'")
    private String companyAddress;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[公司资产 元]'")
    private Double companyAssets;
    @Column(columnDefinition = "datetime comment '[注册时间]'")
    private Date registeredDtm;
    @Column(columnDefinition = "varchar(255) comment '[法定代表人]'")
    private String legalPerson;
    @Column(columnDefinition = "varchar(255) comment '[法定代表人电话]'")
    private String legalPersonPhone;
    @Column(columnDefinition = "varchar(255) comment '[项目负责人]'")
    private String personInCharge;
    @Column(columnDefinition = "varchar(255) comment '[业务员电话]'")
    private String salesmanPhone;


    @Column(columnDefinition = "varchar(255) comment '[资质证明]'")
    private String aptitude;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[近一年的营业额 元]'")
    private Double oneYearTurnover;
    @Column(columnDefinition = "int(1) default 0 comment '[接受银行承兑汇票的结算方式]{0:不接受该结算方式,1:三个月,2:六个月}'")
    private Integer bankDraft;
    @Column(columnDefinition = "int(1) default 0 comment '[能承接的项目规模]{0:均可,1:100KW（含）以下,2:2MW（含）以下,3:20MW（含）以下}'")
    private Integer canWithstand;
    @Column(columnDefinition = "int(1) default 0 comment '[是否具有光伏产业 EPC 总承包集成工程实施能力]{0:否,1:是}'")
    private Integer hadEpc;
    @Column(columnDefinition = "int(11) comment '[最大施工人数]'")
    private Integer maxNumberOfBuilder;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[累计光伏装机容量 KW]'")
    private Double tolCapacityOfYn;
    @Column(columnDefinition = "varchar(255) comment '[营业执照图片地址]'")
    private String businessLicenseImgUrl;
    @Column(columnDefinition = "varchar(255) comment '[资质照片地址]'")
    private String qualificationsImgUrl;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[市场价格 20KW（含）以下   元/瓦]'")
    private Double priceaRing;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[市场价格 20KW以上  元/瓦]'")
    private Double pricebRing;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[设计工程费用 元]'")
    private Double designPrice;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[保修年限 年]'")
    private Double warrantyYear;
    @Column(insertable = false, columnDefinition = "int(11) default 0 comment '[优先级]'")
    private Integer rank;
    @Column(insertable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[服务费率]'")
    private Double factorage;
    @Column(columnDefinition = "varchar(255) NOT NULL comment '[服务商服务城市id]'")
    private String serverCityIds;
    @Column(columnDefinition = "varchar(255) NOT NULL comment '[服务商服务城市]'")
    private String serverCityText;
    @Column(insertable = false, columnDefinition = "int(1) default 0 comment '[是否已经认证]{0:未认证,1:已认证}'")
    private Integer type;


    /**
     * 是否已读
     */
    @Transient
    private Integer isRead;


    /**
     * 用户
     */
    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"password", "role"})
    private User user;

    /**
     * 服务商方案
     */
    @OneToMany
    @JoinColumn(name = "serverId", insertable = false, updatable = false)
    @Where(clause = "del=0")
    @OrderBy("id ASC")
    private Set<NewServerPlan> newServerPlan;

    /**
     * 资质
     */
    @OneToMany
    @JoinColumn(name = "serverId", insertable = false, updatable = true)
    @Where(clause = "del=0")
    private Set<QualificationsServer> qualificationsServer;

    /**
     * 选配项目
     */
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "serverId", insertable = false, updatable = true)
    @Where(clause = "del=0")
    private Set<ApolegamyServer> apolegamyServer;

    public Server() {}

	public Server(String companyName,Double factorage) {
		this.companyName = companyName;
		this.factorage = factorage;
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

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Double getCompanyAssets() {
        return companyAssets;
    }

    public void setCompanyAssets(Double companyAssets) {
        this.companyAssets = companyAssets;
    }

    public Date getRegisteredDtm() {
        return registeredDtm;
    }

    public void setRegisteredDtm(Date registeredDtm) {
        this.registeredDtm = registeredDtm;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPersonPhone() {
        return legalPersonPhone;
    }

    public void setLegalPersonPhone(String legalPersonPhone) {
        this.legalPersonPhone = legalPersonPhone;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public String getSalesmanPhone() {
        return salesmanPhone;
    }

    public void setSalesmanPhone(String salesmanPhone) {
        this.salesmanPhone = salesmanPhone;
    }

    public String getAptitude() {
        return aptitude;
    }

    public void setAptitude(String aptitude) {
        this.aptitude = aptitude;
    }

    public Double getOneYearTurnover() {
        return oneYearTurnover;
    }

    public void setOneYearTurnover(Double oneYearTurnover) {
        this.oneYearTurnover = oneYearTurnover;
    }

    public Integer getBankDraft() {
        return bankDraft;
    }

    public void setBankDraft(Integer bankDraft) {
        this.bankDraft = bankDraft;
    }

    public Integer getCanWithstand() {
        return canWithstand;
    }

    public void setCanWithstand(Integer canWithstand) {
        this.canWithstand = canWithstand;
    }

    public Integer getHadEpc() {
        return hadEpc;
    }

    public void setHadEpc(Integer hadEpc) {
        this.hadEpc = hadEpc;
    }

    public Integer getMaxNumberOfBuilder() {
        return maxNumberOfBuilder;
    }

    public void setMaxNumberOfBuilder(Integer maxNumberOfBuilder) {
        this.maxNumberOfBuilder = maxNumberOfBuilder;
    }

    public Double getTolCapacityOfYn() {
        return tolCapacityOfYn;
    }

    public void setTolCapacityOfYn(Double tolCapacityOfYn) {
        this.tolCapacityOfYn = tolCapacityOfYn;
    }

    public String getBusinessLicenseImgUrl() {
        return businessLicenseImgUrl;
    }

    public void setBusinessLicenseImgUrl(String businessLicenseImgUrl) {
        this.businessLicenseImgUrl = businessLicenseImgUrl;
    }

    public String getQualificationsImgUrl() {
        return qualificationsImgUrl;
    }

    public void setQualificationsImgUrl(String qualificationsImgUrl) {
        this.qualificationsImgUrl = qualificationsImgUrl;
    }

    public Double getPriceaRing() {
        return priceaRing;
    }

    public void setPriceaRing(Double priceaRing) {
        this.priceaRing = priceaRing;
    }

    public Double getPricebRing() {
        return pricebRing;
    }

    public void setPricebRing(Double pricebRing) {
        this.pricebRing = pricebRing;
    }

    public Double getDesignPrice() {
        return designPrice;
    }

    public void setDesignPrice(Double designPrice) {
        this.designPrice = designPrice;
    }

    public Double getWarrantyYear() {
        return warrantyYear;
    }

    public void setWarrantyYear(Double warrantyYear) {
        this.warrantyYear = warrantyYear;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getFactorage() {
        return factorage;
    }

    public void setFactorage(Double factorage) {
        this.factorage = factorage;
    }

    public String getServerCityIds() {
        return serverCityIds;
    }

    public void setServerCityIds(String serverCityIds) {
        this.serverCityIds = serverCityIds;
    }

    public String getServerCityText() {
        return serverCityText;
    }

    public void setServerCityText(String serverCityText) {
        this.serverCityText = serverCityText;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   

    public Set<NewServerPlan> getNewServerPlan() {
		return newServerPlan;
	}

	public void setNewServerPlan(Set<NewServerPlan> newServerPlan) {
		this.newServerPlan = newServerPlan;
	}

	public Set<QualificationsServer> getQualificationsServer() {
        return qualificationsServer;
    }

    public void setQualificationsServer(Set<QualificationsServer> qualificationsServer) {
        this.qualificationsServer = qualificationsServer;
    }

    public Set<ApolegamyServer> getApolegamyServer() {
        return apolegamyServer;
    }

    public void setApolegamyServer(Set<ApolegamyServer> apolegamyServer) {
        this.apolegamyServer = apolegamyServer;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
