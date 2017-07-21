package com.yn.vo;

import com.yn.domain.QueryVo;

public class NoticeVo extends QueryVo{
	
    protected Long id;
    protected Long storeId;
    protected String arriveUrl;
	protected String imgUrl;
    protected String content;
    protected Integer display = 1;
    protected Long schoolId;
    protected String title;
    protected Integer type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getArriveUrl() {
		return arriveUrl;
	}
	public void setArriveUrl(String arriveUrl) {
		this.arriveUrl = arriveUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
