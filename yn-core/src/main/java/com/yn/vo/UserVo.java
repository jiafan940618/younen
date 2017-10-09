package com.yn.vo;

import com.yn.domain.QueryVo;

/**
 * 用户
 */
public class UserVo extends QueryVo {
	
	private Long id;
	private Long userid;
	
	private String token;
	private String phone;
	private String password;
	private String email;
	private String headImgUrl;
	private String nickName;
	private String userName;
	private Integer sex;
	private Long provinceId;
	private String provinceText;
	private Long cityId;
	private String cityText;
	private String addressText;
	private String fullAddressText;
	private String privilegeCodeInit;
	private String privilegeCode;
	private String openIda;
	private String openIdb;
	private String openIdc;
	private Long roleId;

	 /** ioc端备注信息*/
	private String ipoMemo;
	
	
	
	public String getIpoMemo() {
		return ipoMemo;
	}

	public void setIpoMemo(String ipoMemo) {
		this.ipoMemo = ipoMemo;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
