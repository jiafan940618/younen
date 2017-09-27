package com.yn.model;

import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Set;

/**
 * 银行卡
 */
@Entity
public class BankCard extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) comment '[用户id]'")
    private Long userId;
    @Column(columnDefinition = "varchar(255) comment '[真实姓名]'")
    private String realName;
    @Column(columnDefinition = "varchar(255) comment '[身份证号]'")
    private String idCardNum;
    @Column(columnDefinition = "varchar(255) comment '[开户银行]'")
    private String bankName;
    @Column(columnDefinition = "varchar(255) comment '[银行卡号]'")
    private String bankCardNum;
    @Column(columnDefinition = "varchar(255) comment '[预留手机号]'")
    private String phone;
    @Column(columnDefinition = "varchar(255) comment '[账户名称]'")
    private String accountName;
    @Column(columnDefinition = "int(1) comment '[是否已经认证]{0:个人账户,1:工商账户}'")
    private Integer type;
   
    
    @Column(columnDefinition = "int(11) comment '[银行]'")
    private Integer bankId;
    @Column(columnDefinition = "varchar(255) comment '[协议号]'")
    private String treatyId;
    
    @Column(columnDefinition = "varchar(255) comment '[快付通绑定银行卡订单号]'")
    private String orderNo;
    @Column(columnDefinition = "varchar(10) comment '[11、借记卡  12、信用卡]'")
    private String treatyType;
    @Column(columnDefinition = "int(1) comment '[0、存折 1、借记 2、贷记]'")
    private int bank_card_type;
    

    @OneToMany
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Set<BankCode> bankCode;
    

    
    public BankCard() {}

	public BankCard(String treatyId, String orderNo) {
		super();
		this.treatyId = treatyId;
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTreatyType() {
		return treatyType;
	}

	public void setTreatyType(String treatyType) {
		this.treatyType = treatyType;
	}

	public int getBank_card_type() {
		return bank_card_type;
	}

	public void setBank_card_type(int bank_card_type) {
		this.bank_card_type = bank_card_type;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getTreatyId() {
		return treatyId;
	}

	public void setTreatyId(String treatyId) {
		this.treatyId = treatyId;
	}

	public Set<BankCode> getBankCode() {
		return bankCode;
	}

	public void setBankCode(Set<BankCode> bankCode) {
		this.bankCode = bankCode;
	}


	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
