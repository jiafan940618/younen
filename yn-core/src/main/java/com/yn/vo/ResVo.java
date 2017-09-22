package com.yn.vo;

public class ResVo {

	private String title;
	private String content;
	private String target;
	
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
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	@Override
	public String toString() {
		return "ResVo [title=" + title + ", content=" + content + ", target=" + target + "]";
	}
	
}
