package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bank_code")
public class BankCode implements Serializable {
	@Id
	@GeneratedValue
	 @Column(columnDefinition = "int(11) comment '[用户id]'")
	private Long id;
	 @Column(columnDefinition = "varchar(255) comment '[行号]'")
	private String bankNo;
	 @Column(columnDefinition = "varchar(255) comment '[银行名称]'")
	private String bankName;
	 
	 @Column(columnDefinition = "varchar(255) comment '[银行图标]'")
	private String imgUrl;
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	
	

}
