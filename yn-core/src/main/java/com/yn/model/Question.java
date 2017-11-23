package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;

/**
 * 优能疑问
 */
@Entity
public class Question extends IDomain implements Serializable{
	
	@Column(columnDefinition = "varchar(255) comment '[标题]'")
    private String title;
	@Column(columnDefinition = "text comment '[内容]'")
    private String content;
	@Column(columnDefinition = "int(1) comment '[类型]{0:优能疑问}'")
	private Integer type;
	
	public Question() {
		super();
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
