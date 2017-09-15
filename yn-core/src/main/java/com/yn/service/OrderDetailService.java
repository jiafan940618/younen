package com.yn.service;

import com.yn.dao.CommentDao;
import com.yn.dao.mapper.OrderMapper;
import com.yn.model.BillOrder;
import com.yn.model.Comment;
import com.yn.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yn.model.BillOrder;
import com.yn.model.Order;

/**
 * 订单详情服务：为页面每一个按钮单独一个功能
 * 
 * @author Allen
 *
 */
@Service
public class OrderDetailService {

	private Map<String, String> result;

	@Autowired
	private OrderService orderService;

	@Value("${APPLY_PAYMENT_SCALE}")
	private Double APPLY_PAYMENT_SCALE;// 申请中 --> 需：30%

	@Value("${BUILD_PAYMENT_SCALE}")
	private Double BUILD_PAYMENT_SCALE;// 建设中 --> 需：60%

	@Value("${GRIDCONNECTED_PAYMENT_SCALE}")
	private Double GRIDCONNECTED_PAYMENT_SCALE;// 并网发电 --> 需：100%

	@Value("${SURVEYAPPOINTMENTPAYMENT}")
	private Double SURVEYAPPOINTMENTPAYMENT;// 勘察预约 --> 需：25%

	@Autowired
	CommentDao commentDao;

	/**
	 * 贷款申请
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> loanApplication(Order order) {
		result = new HashMap<>();
		if (order != null) {
			double alreadyPaid = 0;// 已支付过的
			Set<BillOrder> billOrder = order.getBillOrder();
			if (billOrder != null) {// 检查有没有用过其他方式支付
				for (BillOrder billOrder2 : billOrder) {
					alreadyPaid += billOrder2.getMoney();
				}
			}
			Double totalPrice = order.getTotalPrice();// 总价
			if (alreadyPaid >= 1) {// 不是第一次。。。
				double needToPay = totalPrice - alreadyPaid;// 还需要贷款的金额
				result.put("needToPay", needToPay + "");
			} else {
				result.put("needToPay", totalPrice.toString());
			}
			order.setLoanStatus(1);// 贷款申请中
			order.setStatus(0);// 订单申请中
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 申请中 --> 线上支付
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> applyPayment(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, APPLY_PAYMENT_SCALE);
		if (needToPay < 0) {
			result.put("needToPay", "0");
			order.setApplyIsPay(1);// 已支付
		} else {
			result.put("needToPay", needToPay.toString());
			order.setApplyIsPay(0);// 未支付
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 建设中 --> 线上支付
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> buildPayment(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, BUILD_PAYMENT_SCALE);
		if (needToPay < 0) {
			result.put("needToPay", "0");
			order.setApplyIsPay(1);// 已支付
		} else {
			result.put("needToPay", needToPay.toString());
			order.setApplyIsPay(0);// 未支付
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 并网申请 --> 线上支付
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> gridConnectedPayment(Order order) {
		/*
		 * result = new HashMap<>(); Double needToPay =
		 * this.calculatedNeedToPayMoney(order, GRIDCONNECTED_PAYMENT_SCALE); if
		 * (needToPay < 0) { result.put("needToPay", "0");
		 * order.setApplyIsPay(1);// 申请中-支付状态 order.setGridConnectedIsPay(1);//
		 * 并网发电-支付状态 order.setGridConnectedStepA(1);// 并网发电-并网状态 --> 1:已申请 }
		 * else { result.put("needToPay", needToPay.toString());
		 * order.setApplyIsPay(0); order.setGridConnectedIsPay(0);
		 * order.setGridConnectedStepA(0); } //更新状态 int byCondition =
		 * orderService.orderService.checkUpdateOrderStatus(order);
		 * result.put("updateOrderStauts", byCondition+""); return result;
		 */
		result = new HashMap<>();
		Double hadPayPrice = order.getHadPayPrice();
		Double totalPrice = order.getTotalPrice();
		// 计算出尾款 :: 100% - 60% --> 40%
		double needToPay = totalPrice - (totalPrice * BUILD_PAYMENT_SCALE);
		if (hadPayPrice != null) {
			if (hadPayPrice >= needToPay) {
				result.put("needToPay", "0");
				order.setApplyIsPay(1);// 申请中-支付状态
				// 修改状态 ： 已支付、已申请、并网发电申请中
				order.setGridConnectedIsPay(1);
				order.setGridConnectedStepA(1);
				order.setStatus(2);
			} else {
				result.put("needToPay", needToPay + "");
				order.setGridConnectedIsPay(0);
				order.setGridConnectedStepA(0);
				order.setApplyIsPay(0);
			}
		} else {
			result.put("needToPay", needToPay + "");
			order.setGridConnectedIsPay(0);
			order.setGridConnectedStepA(0);
			order.setApplyIsPay(0);
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 勘察预约
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> surveyAppointment(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, SURVEYAPPOINTMENTPAYMENT);
		if (needToPay < 0) {
			order.setApplyStepA(1);// 已预约
			result.put("needToPay", "0");
		} else {
			result.put("needToPay", needToPay.toString());
			order.setApplyStepA(0);
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 并网申请（并网发电的线上支付）
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> gridConnectedApplication(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, BUILD_PAYMENT_SCALE);
		if (needToPay < 0) {
			result.put("needToPay", "0");
			// 修改状态 ： 已支付、已申请
			order.setStatus(2);
			order.setApplyIsPay(1);
			order.setGridConnectedIsPay(1);
		} else {
			result.put("needToPay", needToPay.toString());
			order.setStatus(0);
			order.setApplyIsPay(0);
			order.setGridConnectedIsPay(0);
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 施工申请
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> buildApplication(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, BUILD_PAYMENT_SCALE);
		if (needToPay < 0) {
			// 修改状态 : 已支付、已申请、未开始
			order.setBuildIsPay(1);
			order.setBuildStepA(1);
			order.setBuildStepB(0);
			result.put("needToPay", "0");
		} else {
			result.put("needToPay", needToPay.toString());
			order.setBuildIsPay(0);
			order.setBuildStepA(0);
			order.setBuildStepB(0);
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 并网发电
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, String> stationRun(Order order) {
		result = new HashMap<>();
		Double hadPayPrice = order.getHadPayPrice();
		Double totalPrice = order.getTotalPrice();
		// 计算出尾款 :: 100% - 60% --> 40%
		double needToPay = totalPrice - (totalPrice * BUILD_PAYMENT_SCALE);
		if (hadPayPrice >= needToPay) {
			result.put("needToPay", "0");
			// 修改状态 ： 已支付、已申请、并网发电申请中
			order.setGridConnectedIsPay(1);
			order.setGridConnectedStepA(1);
			order.setStatus(2);
		} else {
			result.put("needToPay", needToPay + "");
			order.setGridConnectedIsPay(0);
			order.setGridConnectedStepA(0);
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 并网评分
	 * 
	 * @param comment
	 * @return
	 */
	public boolean pushComment(Comment comment) {
		Comment save = commentDao.save(comment);
		return save.getId() > 0;
	}

	/**
	 * 用于计算需要支付的金额 :: 若已支付的金额大于需支付的金额--> -1 ||--> 需支付的金额
	 * 返回的值小于0，即满足当前需支付的金额，否则返回该阶段需支付的金额
	 * 
	 * @param order
	 *            --> 订单
	 * @param interestRate
	 *            --> 利率
	 * @return
	 */
	private Double calculatedNeedToPayMoney(Order order, Double interestRate) {
		Double hadPayPrice = order.getHadPayPrice();// 已支付
		Double totalPrice = order.getTotalPrice();// 总价
		Double needToPay = totalPrice * interestRate;// 需要支付的金额
		if (hadPayPrice == null) {
			return needToPay;
		}
		// System.out.println("hadPayPrice :: "+hadPayPrice);
		// System.out.println("totalPrice :: "+totalPrice);
		// System.out.println("needToPay :: "+needToPay);
		if (hadPayPrice >= needToPay)
			return -1d;
		return needToPay;
	}

}
