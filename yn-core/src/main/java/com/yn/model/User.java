package com.yn.model;

import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;


/**
 * 用户
 */
@Entity
public class User extends IDomain implements Serializable {
	
	@Column(columnDefinition = "varchar(255) comment '[登陆token]'")
	protected String token;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[手机号码]'")
	protected String phone;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[密码]'")
	protected String password;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[邮箱]'")
	protected String email;
	@Column(columnDefinition = "varchar(255) comment '[头像]'")
	protected String headImgUrl;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[昵称]'")
	protected String nickName;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[用户名]'")
	protected String userName;
	@Column(columnDefinition = "int(1) NOT NULL comment '[性别]{0:未知,1:男,2:女}'")
	protected Integer sex;
	@Column(columnDefinition = "int(11) NOT NULL comment '[省id]'")
	protected Integer provinceId;
    @Column(columnDefinition = "varchar(255) NOT NULL comment '[省地址]'")
	private String provinceText;
	@Column(columnDefinition = "int(11) NOT NULL comment '[市id]'")
	protected Integer cityId;
    @Column(columnDefinition = "varchar(255) NOT NULL comment '[市地址]'")
    private String cityText;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[详细地址]'")
	protected String addressText;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[全地址]'")
	protected String fullAddressText;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[注册码]'")
	protected String privilegeCodeInit;
	@Column(columnDefinition = "varchar(255) comment '[注册时使用的注册码]'")
	protected String privilegeCode;
	@Column(columnDefinition = "varchar(255) comment '[openIda]'")
	protected String openIda;
	@Column(columnDefinition = "varchar(255) comment '[openIdb]'")
	protected String openIdb;
	@Column(columnDefinition = "varchar(255) comment '[openIdc]'")
	protected String openIdc;
	@Column(columnDefinition = "int(11) NOT NULL comment '[角色id]'")
	protected Long roleId;
	
	/**
	 * 银行卡
	 */
	@OneToMany
    @JoinColumn(name = "userId", insertable = false, updatable = true)
    private Set<BankCard> bankCard;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getFullAddressText() {
        return fullAddressText;
    }

    public void setFullAddressText(String fullAddressText) {
        this.fullAddressText = fullAddressText;
    }

    public String getPrivilegeCodeInit() {
        return privilegeCodeInit;
    }

    public void setPrivilegeCodeInit(String privilegeCodeInit) {
        this.privilegeCodeInit = privilegeCodeInit;
    }

    public String getPrivilegeCode() {
        return privilegeCode;
    }

    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode;
    }

    public String getOpenIda() {
        return openIda;
    }

    public void setOpenIda(String openIda) {
        this.openIda = openIda;
    }

    public String getOpenIdb() {
        return openIdb;
    }

    public void setOpenIdb(String openIdb) {
        this.openIdb = openIdb;
    }

    public String getOpenIdc() {
        return openIdc;
    }

    public void setOpenIdc(String openIdc) {
        this.openIdc = openIdc;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Set<BankCard> getBankCard() {
        return bankCard;
    }

    public void setBankCard(Set<BankCard> bankCard) {
        this.bankCard = bankCard;
    }

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }
}
