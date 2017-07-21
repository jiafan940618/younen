package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * 用户
 */
@Entity
public class User extends IDomain implements Serializable {
	
	@Column(columnDefinition = "varchar(255) comment '[手机号码]'")
	protected String token;
	@Column(columnDefinition = "varchar(255) comment '[手机号码]'")
	protected String phone;
	@Column(columnDefinition = "varchar(255) comment '[密码]'")
	protected String password;
	@Column(columnDefinition = "varchar(255) comment '[邮箱]'")
	protected String email;
	@Column(columnDefinition = "varchar(255) comment '[头像]'")
	protected String headImgUrl;
	@Column(columnDefinition = "varchar(255) comment '[昵称]'")
	protected String nickName;
	@Column(columnDefinition = "varchar(255) comment '[用户名]'")
	protected String userName;
	@Column(columnDefinition = "int(1) default 0 comment '[性别]{0:未知,1:男,2:女}'")
	protected Integer sex;
	@Column(columnDefinition = "int(11) comment '[省id]'")
	protected Integer provinceId;
	@Column(columnDefinition = "int(11) comment '[市id]'")
	protected Integer cityId;
	@Column(columnDefinition = "varchar(255) comment '[地址]'")
	protected String addressText;
	@Column(columnDefinition = "varchar(255) comment '[全地址]'")
	protected String fullAddressText;
	@Column(columnDefinition = "varchar(255) comment '[注册码]'")
	protected String privilegeCodeInit;
	@Column(columnDefinition = "varchar(255) comment '[注册时使用的注册码]'")
	protected String privilegeCode;
	@Column(columnDefinition = "varchar(255) comment '[openIda]'")
	protected String openIda;
	@Column(columnDefinition = "varchar(255) comment '[openIdb]'")
	protected String openIdb;
	@Column(columnDefinition = "varchar(255) comment '[openIdc]'")
	protected String openIdc;
	@Column(columnDefinition = "int(11) comment '[角色id]'")
	protected Integer roleId;
	
	/**
	 * 角色
	 */
//	@OneToOne
//    @JoinColumn(name="roleId", insertable = false, updatable=false)
//    protected Role role;
	
	/**
	 * 银行卡
	 */
	@OneToMany
    @JoinColumn(name = "userId", insertable = false, updatable = true)
    private Set<BankCard> bankCard;
	
	
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
//	public Role getRole() {
//		Map<Menu, List<Menu>> map = new LinkedHashMap<>();
//        Set<Menu> menus = role.getMenu();
//        for (Menu menu : menus) {
//			if (menu.getZlevel() == 1) {
//				map.put(menu, new ArrayList<>());
//			}
//		}
//
//        Set<Menu> keySet = map.keySet();
//        for (Menu parent : keySet) {
//        	List<Menu> children = map.get(parent);
//			for (Menu menu : menus) {
//				if (menu.getZlevel() != 1) {
//					if (menu.getParentId().intValue() == parent.getId().intValue()) {
//						children.add(menu);
//					}
//				}
//			}
//			parent.setChildren(children);
//		}
//        role.setMenu(keySet);
//
//		return role;
//	}
//	public void setRole(Role role) {
//		this.role = role;
//	}
	public Set<BankCard> getBankCard() {
		return bankCard;
	}
	public void setBankCard(Set<BankCard> bankCard) {
		this.bankCard = bankCard;
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
