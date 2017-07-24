package com.yn.model;

import com.yn.domain.IDomain;
import lombok.Data;

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
@Data
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
	@Column(columnDefinition = "int(11) NOT NULL comment '[角色id]'")
	protected Integer roleId;
	
	/**
	 * 银行卡
	 */
	@OneToMany
    @JoinColumn(name = "userId", insertable = false, updatable = true)
    private Set<BankCard> bankCard;
}
