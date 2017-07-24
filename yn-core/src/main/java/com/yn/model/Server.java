package com.yn.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;
import lombok.Data;
import org.hibernate.annotations.Where;

/**
 * 服务商
 */
@Entity
@Data
public class Server extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) default 0 comment '[用户id]'")
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
    @Column(columnDefinition = "varchar(255) comment '[服务商服务城市id]'")
    private String serverCityIds;
    @Column(columnDefinition = "varchar(255) comment '[服务商服务城市]'")
    private String serverCityText;
    @Column(insertable = false, updatable = true, columnDefinition = "int(1) default 0 comment '[是否已经认证]{0:未认证,1:已认证}'")
    private Integer type;

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
    private Set<ServerPlan> serverPlan;

    /**
     * 资质
     */
    @ManyToMany
    private Set<Qualifications> qualifications;

    /**
     * 选配项目
     */
    @OneToMany
    @JoinColumn(name = "serverId", insertable = false, updatable = false)
    @Where(clause = "del=0")
    private Set<ApolegamyServer> apolegamyServer;
}
