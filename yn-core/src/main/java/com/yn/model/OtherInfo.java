package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

@Entity
public class OtherInfo extends IDomain implements Serializable{
	
		@Id
	    @GeneratedValue
	    @Column(columnDefinition = "int(11) comment '[id]'")
	    private Long id;
		@Column(columnDefinition = "int(3) comment '[品牌编号]'")
		private Integer brandId;
		@Column(columnDefinition = "varchar(255) comment '[品牌名]'")
		private String brandName;
		@Column(columnDefinition = "varchar(255) comment '[型号]'")
		private String model;
		
	
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Integer getBrandId() {
			return brandId;
		}
		public void setBrandId(Integer brandId) {
			this.brandId = brandId;
		}
		public String getBrandName() {
			return brandName;
		}
		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
}
