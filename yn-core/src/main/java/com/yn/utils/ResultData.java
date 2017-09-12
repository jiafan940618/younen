package com.yn.utils;

import java.util.List;

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

		public Boolean getSuccess() {
	        return success;
	    }

	    public void setSuccess(Boolean success) {
	        this.success = success;
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