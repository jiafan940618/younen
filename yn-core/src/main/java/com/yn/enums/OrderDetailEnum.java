package com.yn.enums;

/**
 * Created by Allen on 2017/9/6.
 */
public enum OrderDetailEnum {

	LOANAPPLICATION(0, "贷款申请"), 
	APPLYPAYMENT(1, "申请中线上支付"), 
	BUILDPAYMENT(2, "建设中线上支付"), 
	GRIDCONNECTEDPAYMENT(3, "并网申请线上支付"), 
	SURVEYAPPOINTMENT(4, "勘察预约"), 
	GRIDCONNECTEDAPPLICATION(5, "并网申请"), 
	BUILDAPPLICATION(6, "施工申请"), 
	STATIONRUN(7, "并网发电");
	/*LOANAPPLICATION, 
	APPLYPAYMENT, 
	BUILDPAYMENT, 
	GRIDCONNECTEDPAYMENT, 
	SURVEYAPPOINTMENT, 
	GRIDCONNECTEDAPPLICATION, 
	BUILDAPPLICATION, 
	STATIONRUN;*/

	private Integer code;
	private String message;

	private OrderDetailEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
