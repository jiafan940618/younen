package com.yn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yn.dao.CommentDao;
import com.yn.model.BillOrder;
import com.yn.model.Comment;
import com.yn.model.Order;

/**
 * 订单详情服务：为页面每一个按钮单独一个功能
 * 
 * @author Allen
 *
 */
@Service
public class OrderDetailService {

	private Map<String, Object> result;

	@Autowired
	private OrderService orderService;

	@Autowired
	private BillOrderService billOrderService;

	@Value("${APPLY_PAYMENT_SCALE}")
	private Double APPLY_PAYMENT_SCALE;// 申请中 --> 需：30%

	@Value("${BUILD_PAYMENT_SCALE}")
	private Double BUILD_PAYMENT_SCALE;// 建设中 --> 需：60%

	@Value("${GRIDCONNECTED_PAYMENT_SCALE}")
	private Double GRIDCONNECTED_PAYMENT_SCALE;// 并网发电 --> 需：100%

	@Value("${SURVEYAPPOINTMENTPAYMENT}")
	private Double SURVEYAPPOINTMENTPAYMENT;// 勘察预约 --> 需：固定为5000

	@Autowired
	CommentDao commentDao;

	/**
	 * 贷款申请
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> loanApplication(Order order) {
		result = new HashMap<>();
		order = orderService.findOne(order.getId());
		if (order != null) {
			double alreadyPaid = 0;// 已支付过的
			if (order.getHadPayPrice() != null) {
				alreadyPaid = order.getHadPayPrice();
			}
			Double totalPrice = order.getTotalPrice();// 总价
			if (alreadyPaid >= 1) {// 不是第一次。。。
				double needToPay = totalPrice - alreadyPaid;// 还需要贷款的金额
				result.put("needToPay", needToPay);
			} else {
				result.put("needToPay", totalPrice);
			}
			order.setLoanStatus(1);// 贷款申请中
			order.setStatus(0);// 订单申请中
		}
		// 更新状态 --> success：true
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition);
		return result;
	}

	/**
	 * 申请中 --> 线上支付
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> applyPayment(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, APPLY_PAYMENT_SCALE);
		if (needToPay < 0) {
			result.put("needToPay", 0);
			// order.setApplyIsPay(1);// 已支付
		} else {
			result.put("needToPay", needToPay);
			// order.setApplyIsPay(0);// 未支付
		}
		result.put("nickName", order.getUser().getNickName());
		// 更新状态 --> success：true
		// boolean byCondition = orderService.checkUpdateOrderStatus(order);
		// result.put("updateOrderStauts", byCondition + "");
		// 查看有没有生成订单记录
		return result;
	}

	/**
	 * 建设中 --> 线上支付
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> buildPayment(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, BUILD_PAYMENT_SCALE);
		if (needToPay < 0) {
			result.put("needToPay", 0);
			// order.setApplyIsPay(1);// 已支付
		} else {
			result.put("needToPay", needToPay);
			// order.setApplyIsPay(0);// 未支付
		}
		// 更新状态 --> success：true
		// boolean byCondition = orderService.checkUpdateOrderStatus(order);
		// result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 并网申请 --> 线上支付
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> gridConnectedPayment(Order order) {
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
				result.put("needToPay", 0);
				// order.setApplyIsPay(1);// 申请中-支付状态
				// 修改状态 ： 已支付、已申请、并网发电申请中
				// order.setGridConnectedIsPay(1);
				// order.setGridConnectedStepA(1);
				// order.setStatus(2);
			} else {
				result.put("needToPay", needToPay);
				// order.setGridConnectedIsPay(0);
				// order.setGridConnectedStepA(0);
				// order.setApplyIsPay(0);
			}
		} else {
			result.put("needToPay", needToPay);
			// order.setGridConnectedIsPay(0);
			// order.setGridConnectedStepA(0);
			// order.setApplyIsPay(0);
		}
		// 更新状态 --> success：true
		// boolean byCondition = orderService.checkUpdateOrderStatus(order);
		// result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 勘察预约
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> surveyAppointment(Order order) {
		result = new HashMap<>();
		// Double needToPay = calculatedNeedToPayMoney(order,
		// SURVEYAPPOINTMENTPAYMENT);
		Order one = orderService.findOne(order.getId());
		Double hadPayPrice = one.getHadPayPrice();
		if (hadPayPrice >= SURVEYAPPOINTMENTPAYMENT) {
			result.put("needToPay", 0);
		} else {
			result.put("needToPay", SURVEYAPPOINTMENTPAYMENT);
		}
		// 更新状态 --> success：true
		// boolean byCondition = orderService.checkUpdateOrderStatus(order);
		// result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 并网申请（并网发电的线上支付）
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> gridConnectedApplication(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, BUILD_PAYMENT_SCALE);
		if (needToPay < 0) {
			result.put("needToPay", 0);
			// 修改状态 ： 已支付、已申请
			// order.setStatus(2);
			// order.setApplyIsPay(1);
			// order.setGridConnectedIsPay(1);
		} else {
			result.put("needToPay", needToPay);
			// order.setStatus(0);
			// order.setApplyIsPay(0);
			// order.setGridConnectedIsPay(0);
		}
		// 更新状态 --> success：true
		// boolean byCondition = orderService.checkUpdateOrderStatus(order);
		// result.put("updateOrderStauts", byCondition + "");
		return result;
	}

	/**
	 * 施工申请
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> buildApplication(Order order) {
		result = new HashMap<>();
		Double needToPay = calculatedNeedToPayMoney(order, BUILD_PAYMENT_SCALE);
		if (needToPay < 0) {
			// 修改状态 : 已支付、已申请、未开始
			order.setBuildIsPay(1);
			order.setBuildStepA(1);
			order.setBuildStepB(0);
			result.put("needToPay", 0);
		} else {
			result.put("needToPay", needToPay);
			order.setBuildIsPay(0);
			order.setBuildStepA(0);
			order.setBuildStepB(0);
		}
		// 更新状态 --> success：true
		result.put("buildIsPay", order.getBuildIsPay());
		result.put("buildStepB", order.getBuildStepB());
		boolean byCondition = orderService.checkUpdateOrderStatus(order);
		result.put("updateOrderStauts", byCondition);
		return result;
	}

	/**
	 * 并网发电
	 * 
	 * @param order
	 * @return
	 */
	public Map<String, Object> stationRun(Order order) {
		result = new HashMap<>();
		Double hadPayPrice = order.getHadPayPrice();
		Double totalPrice = order.getTotalPrice();
		// 计算出尾款 :: 100% - 60% --> 40%
		double needToPay = totalPrice - (totalPrice * BUILD_PAYMENT_SCALE);
		if (hadPayPrice >= needToPay) {
			result.put("needToPay", 0);
			// 修改状态 ： 已支付、已申请、并网发电申请中
			// order.setGridConnectedIsPay(1);
			// order.setGridConnectedStepA(1);
			// order.setStatus(2);
		} else {
			result.put("needToPay", needToPay);
			// order.setGridConnectedIsPay(0);
			// order.setGridConnectedStepA(0);
		}
		// 更新状态 --> success：true
		// boolean byCondition = orderService.checkUpdateOrderStatus(order);
		// result.put("updateOrderStauts", byCondition + "");
		/* --先判断是贷款还是线上支付的 以下是ios需要的-- */
		// 贷款不成功,并网未支付
		boolean flag = true;
		if (order.getGridConnectedIsPay() > 0 && order.getLoanStatus() != 2) {
			flag = false;
			result.put("isPowerGeneration", false);
		}
		if (order.getGridConnectedStepA() != 2) {
			flag = false;
			result.put("isPowerGeneration", false);
		}
		if (order.getStatus() != 3) {
			flag = false;
			result.put("isPowerGeneration", false);
		}
		if (order.getHadPayPrice() < order.getTotalPrice()) {
			flag = false;
			result.put("isPowerGeneration", false);
		}
		// 可以发电、ios需要
		if (flag) {
			result.put("isPowerGeneration", true);
		}
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
	public Double calculatedNeedToPayMoney(Order order, Double interestRate) {
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
