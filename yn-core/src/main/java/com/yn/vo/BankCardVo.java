package com.yn.vo;

/**
 * 银行卡
 */
public class BankCardVo {
	
	private Long id;
	private Long userId;
	private String realName;
	private String idCardNum;
	private String bankName;
	private String bankCardNum;
	private String phone;
	private String accountName;
	private Integer type;
	
	private String bankNo;
	
	private Integer bankId;

	private String treatyId;
	    
	private String orderNo;

	private String treatyType;

	private int bank_card_type;
	      
	 private String img_url;   
	    
	    
	    public String getImg_url() {
	    	return img_url;
	    }
	    public void setImg_url(String img_url) {
	    	this.img_url = img_url;
	    }
		public String getBankNo() {
			return bankNo;
		}
		public void setBankNo(String bankNo) {
			this.bankNo = bankNo;
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
