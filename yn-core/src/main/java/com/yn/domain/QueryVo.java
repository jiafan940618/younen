package com.yn.domain;

public class QueryVo implements IQuery{

	String query; // 查询的内容
	String queryStartDtm; // 查询的开始日期
	String queryEndDtm; // 查询的结束日期

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getQueryStartDtm() {
		return queryStartDtm;
	}
	public void setQueryStartDtm(String queryStartDtm) {
		this.queryStartDtm = queryStartDtm;
	}
	public String getQueryEndDtm() {
		return queryEndDtm;
	}
	public void setQueryEndDtm(String queryEndDtm) {
		this.queryEndDtm = queryEndDtm;
	}
}
