package com.yn.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.BillOrder;
import com.yn.model.Order;
import com.yn.service.ApolegamyService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderDetailService;
import com.yn.service.OrderService;
import com.yn.utils.ResultData;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/test")
public class TestController {

	@Autowired
	private NewServerPlanService planService;

	@Autowired
	ApolegamyService apolegamyService;

	@Autowired
	OrderDetailService ods;

	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private OrderService orderService;

	@Value("${APPLY_PAYMENT_SCALE}")
	private Double APPLY_PAYMENT_SCALE;
	@Value("${BUILD_PAYMENT_SCALE}")
	private Double BUILD_PAYMENT_SCALE;
	@Value("${GRIDCONNECTED_PAYMENT_SCALE}")
	private Double GRIDCONNECTED_PAYMENT_SCALE;

	private static DecimalFormat df = new DecimalFormat("0.00");
	private static DecimalFormat df1 = new DecimalFormat("0000");
	private static Random rd = new Random();
	private static SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };
	/** (不能与自定义进制有重复) */
	private static final char b = 'o';
	/** 进制长度 */
	private static final int binLen = r.length;

	public static String toSerialCode(long id, int s) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}

	@RequestMapping(value = "/dotest")
	@ResponseBody
	public ResultData<Object> newTest() {

		List<Object> list = planService.selectServerPlan(1L);

		return ResultVOUtil.success(list);
	}

	@RequestMapping(value = "/dotest02")
	@ResponseBody
	public ResultData<Object> someTest() {
		return ResultVOUtil.success(null);
	}

	@RequestMapping(value = "/testResourceValue")
	@ResponseBody
	public ResultData<Object> testResourceValue() {
		String msg = "申请中::" + APPLY_PAYMENT_SCALE + ", 建设中::" + BUILD_PAYMENT_SCALE + " ,并网发电::"
				+ GRIDCONNECTED_PAYMENT_SCALE;
		System.out.println(msg);
		return ResultVOUtil.success(msg);
	}

	@RequestMapping(value = "/testOrderDetailServiceMethod")
	@ResponseBody
	public ResultData<Object> testOrderDetailServiceMethod() {
		Order order = new Order();
		order.setId(19l);
		order.setHadPayPrice(6000d);
		order.setTotalPrice(20000d);
		order.setApplyIsPay(1);
		/*
		 * Set<BillOrder> billOrder = new HashSet<BillOrder>(); for (int i = 0;
		 * i <= 1; i++) { BillOrder bo = new BillOrder(); bo.setMoney(1000d);
		 * billOrder.add(bo); } order.setBillOrder(billOrder);
		 */
		Map<String, String> payment = orderDetailService.applyPayment(order);
		// Map<String, String> loanApplication =
		// orderDetailService.loanApplication(order);
		// Map<String, String> stationRun =
		// orderDetailService.stationRun(order);
		return ResultVOUtil.success(payment);
	}

}
