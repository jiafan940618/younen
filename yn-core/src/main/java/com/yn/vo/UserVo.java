package com.yn.vo;

import com.yn.domain.QueryVo;

/**
 * 用户
 */
public class UserVo extends QueryVo {
	
	private Long id;
	protected String token;
	protected String phone;
	protected String password;
	protected String email;
	protected String headImgUrl;
	protected String nickName;
	protected String userName;
	protected Integer sex;
	protected Integer provinceId;
	protected Integer cityId;
	protected String addressText;
	protected String fullAddressText;
	protected String privilegeCodeInit;
	protected String privilegeCode;
	protected String openIda;
	protected String openIdb;
	protected String openIdc;
	protected Integer roleId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
