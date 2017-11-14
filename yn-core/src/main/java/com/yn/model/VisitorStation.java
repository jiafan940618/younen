package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** 
 * 业务管理电站实体类
 * 
 * */
@Entity
public class VisitorStation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    @Column(columnDefinition = "int(11) comment '[id]'")
	private Long id;
	
	private Long userId;
	
	private String stationIds;

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
	
		public String getStationIds() {
			return stationIds;
		}
	
		public void setStationIds(String stationIds) {
			this.stationIds = stationIds;
		}

}
