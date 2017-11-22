package com.yn.model;

import java.util.List;

import com.yn.utils.PropertyUtils;

public class Page<T> {

	private Integer limit =Integer.valueOf(PropertyUtils.getProperty("limit"));
	private List<T> list;
	private T example;

	String sort = "create_dtm";
	String sortUp = "desc";
	Integer total = 0;
	Integer start = 0;
	Integer index = -1;
	

	private Long userId;
	private Integer status;
	private String time_from;
	private String time_to;
	
	private Integer type = 1;

	private Integer payWay;
	private Integer isRead;
	
	private String cityName;
	
	private Long id;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
		if (index != -1) {
			start = limit * (index - 1);
		}
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
		start = limit * (index - 1);
	}

	public T getExample() {
		return example;
	}

	public void setExample(T example) {
		this.example = example;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortUp() {
		return sortUp;
	}

	public void setSortUp(String sortUp) {
		this.sortUp = sortUp;
	}

	public String getTime_from() {
		return time_from;
	}

	public void setTime_from(String time_from) {
		this.time_from = time_from;
	}

	public String getTime_to() {
		return time_to;
	}

	public void setTime_to(String time_to) {
		this.time_to = time_to;
	}
}