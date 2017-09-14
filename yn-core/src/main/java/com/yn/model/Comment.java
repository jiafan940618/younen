package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * t_Comment
 * 
 * @author hy-03
 *
 */
@Entity
@Table(name="t_comment")
public class Comment extends IDomain implements Serializable {
	@Id
	@GeneratedValue
	@Column(columnDefinition = "int(11) comment '[id]'")
	private Long id;
	
	@Column(precision = 5, scale = 2, columnDefinition = "decimal(5,2) NOT NULL comment '[服务质量]'")
	private Double serverQuality;
	
	@Column(precision = 5, scale = 2, columnDefinition = "decimal(5,2) NOT NULL comment '[服务效率]'")
	private Double serverEfficiency;
	
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[详细评价]'")
	private String detailedEvaluation;

	@Column(columnDefinition = "int(11) NOT NULL comment '[订单id]'")
	private Long orderId;

	public Double getServerQuality() {
		return serverQuality;
	}

	public void setServerQuality(Double serverQuality) {
		this.serverQuality = serverQuality;
	}

	public Double getServerEfficiency() {
		return serverEfficiency;
	}

	public void setServerEfficiency(Double serverEfficiency) {
		this.serverEfficiency = serverEfficiency;
	}

	public String getDetailedEvaluation() {
		return detailedEvaluation;
	}

	public void setDetailedEvaluation(String detailedEvaluation) {
		this.detailedEvaluation = detailedEvaluation;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
