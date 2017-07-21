package com.yn.exception;

public class MyException extends RuntimeException {

	private Integer code;

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public MyException(Integer code, String message) {
		super(message);
		this.code = code;
	}
}
