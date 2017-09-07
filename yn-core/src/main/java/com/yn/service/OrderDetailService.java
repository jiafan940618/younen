package com.yn.service;

import com.yn.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单详情服务：为页面每一个按钮单独一个功能
 * 
 * @author Allen
 *
 */
@Service
public class OrderDetailService {

	private Map<String, String> result;
	private Double APPLY_PAYMENT_SCALE = 0.3;
	private Double BUILD_PAYMENT_SCALE = 0.7;
	private Double GRIDCONNECTED_PAYMENT_SCALE = 1.0;
	private Double SURVEYAPPOINTMENTPAYMENT = 5000.0;

	public Map<String, String> loanApplication(Order order) {
		result = new HashMap<>();

		return result;
	}

	public Map<String, String> applyPayment(Order order) {
		result = new HashMap<>();
		Double hadPayPrice = order.getHadPayPrice();
		Double needToPay = order.getTotalPrice() * APPLY_PAYMENT_SCALE;
		if (hadPayPrice >= needToPay) {
			result.put("needToPay", "0");
		} else {
			result.put("needToPay", needToPay.toString());
		}
		return result;
	}

	public Map<String, String> buildPayment(Order order) {
		result = new HashMap<>();
		Double hadPayPrice = order.getHadPayPrice();
		Double needToPay = order.getTotalPrice() * BUILD_PAYMENT_SCALE;
		if (hadPayPrice >= needToPay) {
			result.put("needToPay", "0");
		} else {
			result.put("needToPay", needToPay.toString());
		}
		return result;
	}

	public Map<String, String> gridConnectedPayment(Order order) {
		result = new HashMap<>();
		Double hadPayPrice = order.getHadPayPrice();
		Double needToPay = order.getTotalPrice() * GRIDCONNECTED_PAYMENT_SCALE;
		if (hadPayPrice >= needToPay) {
			result.put("needToPay", "0");
		} else {
			result.put("needToPay", needToPay.toString());
		}
		return result;
	}

	public Map<String, String> surveyAppointment(Order order) {
		result = new HashMap<>();
		Double hadPayPrice = order.getHadPayPrice();
		if(hadPayPrice>=SURVEYAPPOINTMENTPAYMENT){
			//order.setApplyStepA(applyStepA);
		}
		return result;
	}

	public Map<String, String> gridConnectedApplication(Order order) {
		result = new HashMap<>();
		return result;
	}

	public Map<String, String> buildApplication(Order order) {
		result = new HashMap<>();
		return result;
	}

	public Map<String, String> stationRun(Order order) {
		result = new HashMap<>();
		return result;
	}

}
