package com.yn.vo;

public class ResVo {

	private String title;
	private String content;
	private String target;
	private boolean isNow = false;

	

	public boolean getIsNow() {
		return isNow;
	}
	public void setIsNow(boolean isNow) {
		this.isNow = isNow;
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
	public ResVo(boolean isNow) {
		this.isNow = isNow;
	}
	public ResVo() {
		this.isNow = false;
	}
	
	
}
