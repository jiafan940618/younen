package com.yn.utils;

import java.util.List;
import java.util.Map;

import com.yn.model.Apolegamy;
import com.yn.model.ProductionDetail;
import com.yn.vo.PriceVo;

public class ResultData<T> {

	 private T data;

	    private int code = 200;

	    private String msg;

	    private Boolean success = true;
	    
	    private List<T> list;
	    
	    private List<T> newlist;

		private T produ;
		
		private Map<String, String> map;

		private Object object;
		
		
		
		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		public Map<String, String> getMap() {
			return map;
		}

		public void setMap(Map<String, String> map) {
			this.map = map;
		}

		public Boolean getSuccess() {
			return success;
		}

		public void setSuccess(Boolean success) {
			this.success = success;
		}

		

		public List<T> getNewlist() {
			return newlist;
		}

		public void setNewlist(List<T> newlist) {
			this.newlist = newlist;
		}

		public T getProdu() {
			return produ;
		}

		public void setProdu(T produ) {
			this.produ = produ;
		}

		public List<T> getList() {
			return list;
		}

		public void setList(List<T> list) {
			this.list = list;
		}

		public T getData() {
	        return data;
	    }

	    public void setData(T data) {
	        this.data = data;
	    }

	    public int getCode() {
	        return code;
	    }

	    public void setCode(int code) {
	        if (code != 200) {
	            success = false;
	        }
	        this.code = code;
	    }

	    public String getMsg() {
	        return msg;
	    }

	    public void setMsg(String msg) {
	        this.msg = msg;
	    }
}