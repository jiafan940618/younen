package com.yn.vo;

import java.util.Date;
import java.util.Set;

import com.yn.domain.QueryVo;
import lombok.Data;

/**
 * 服务商
 */
@Data
public class ServerVo extends QueryVo{
	
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
	private Double design_price;
	private Double warrantyYear;
	private Integer rank;
	private Double factorage;
	private String serverCityIds;
	private String serverCityText;
	private Integer type;
	
	private Set<QualificationsVo> qualifications;
}
