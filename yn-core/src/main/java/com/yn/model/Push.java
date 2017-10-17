package com.yn.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

@Entity
public class Push extends IDomain implements Serializable{

	@Column(columnDefinition = "int(11) comment '[用户id]'")
	private Long userId;
	@Column(columnDefinition = "varchar(255) comment '[标题]'")
	private String title;
	@Column(columnDefinition = "text comment '[内容]'")
	private String content;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[是否已读]{0:未读,1:已读,2:全部}'")
	private Integer isRead;
	@Column(columnDefinition = "int(1) comment '[类型]{0:推送给个人,1:推送给所有人}'")
	private Integer type;
	
	/**
	 * 用户
	 */
	@ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"password","role","bankCard"})
    private User user;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
