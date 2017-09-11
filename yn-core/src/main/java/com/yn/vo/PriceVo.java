package com.yn.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;

public class PriceVo {
	
	
	private Long id;

	private BigDecimal  Allmoney;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getAllmoney() {
		return Allmoney;
	}


	public void setAllmoney(BigDecimal allmoney) {
		this.Allmoney = allmoney;
	}



	
	
	

}
