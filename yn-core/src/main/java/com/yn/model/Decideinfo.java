package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.yn.domain.IDomain;

@Entity
public class Decideinfo  extends IDomain implements Serializable{
	
	@Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) comment '[id]'")
    private Long id;
	
	/** 逆变器的*/
	private Long inverterId;
	private String invremark;
	private Double invsupplyPrice;
	
	
	
	/** 电池板的*/
	private Long solarpanelId;
	
	private String solremark;
	private Double solsupplyPrice;
	
	private Long serverId;
	
	
	
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getInvremark() {
		return invremark;
	}
	public void setInvremark(String invremark) {
		this.invremark = invremark;
	}
	public Double getInvsupplyPrice() {
		return invsupplyPrice;
	}
	public void setInvsupplyPrice(Double invsupplyPrice) {
		this.invsupplyPrice = invsupplyPrice;
	}
	
	
	public Long getInverterId() {
		return inverterId;
	}
	public void setInverterId(Long inverterId) {
		this.inverterId = inverterId;
	}
	public Long getSolarpanelId() {
		return solarpanelId;
	}
	public void setSolarpanelId(Long solarpanelId) {
		this.solarpanelId = solarpanelId;
	}
	public String getSolremark() {
		return solremark;
	}
	public void setSolremark(String solremark) {
		this.solremark = solremark;
	}
	public Double getSolsupplyPrice() {
		return solsupplyPrice;
	}
	public void setSolsupplyPrice(Double solsupplyPrice) {
		this.solsupplyPrice = solsupplyPrice;
	}
}
