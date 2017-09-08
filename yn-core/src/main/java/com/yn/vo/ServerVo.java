package com.yn.vo;

import com.yn.domain.QueryVo;
import com.yn.model.Devide;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.management.loading.PrivateClassLoader;


/**
 * 服务商
 */
public class ServerVo extends QueryVo {

    private Long id;
    private Long userId;
    private String companyEmail;
    private String companyLogo;
    private String companyName;
    private String companyAddress;
    private Double companyAssets;
    private Date registeredDtm;
    private String legalPerson;
    private String legalPersonPhone;
    private String personInCharge;
    private String salesmanPhone;
    private String aptitude;
    private Double oneYearTurnover;
    private Integer bankDraft;
    private Integer canWithstand;
    private Integer hadEpc;
    private Integer maxNumberOfBuilder;
    private Double tolCapacityOfYn;
    private String businessLicenseImgUrl;
    private String qualificationsImgUrl;
    private Double priceaRing;
    private Double pricebRing;
    private Double designPrice;
    private Double warrantyYear;
    private Integer rank;
    private Double factorage;
    private String serverCityIds;
    private String serverCityText;
    private Integer type;
    
    private String password;
    private String phone;

    private Set<QualificationsServerVo> qualificationsServer;
    private Set<ApolegamyServerVo> apolegamyServer;
    
    private List<Devide> device;
    
    
    public List<Devide> getDevice() {
		return device;
	}

	public void setDevice(List<Devide> device) {
		this.device = device;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

    public Set<QualificationsServerVo> getQualificationsServer() {
        return qualificationsServer;
    }

    public void setQualificationsServer(Set<QualificationsServerVo> qualificationsServer) {
        this.qualificationsServer = qualificationsServer;
    }

    public Set<ApolegamyServerVo> getApolegamyServer() {
        return apolegamyServer;
    }

    public void setApolegamyServer(Set<ApolegamyServerVo> apolegamyServer) {
        this.apolegamyServer = apolegamyServer;
    }

    public Double getDesignPrice() {
        return designPrice;
    }

    public void setDesignPrice(Double designPrice) {
        this.designPrice = designPrice;
    }
}
