package com.yn.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.model.Apolegamy;
import com.yn.model.BankCard;
import com.yn.model.BillOrder;
import com.yn.model.NewServerPlan;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.Page;
import com.yn.model.Recharge;
import com.yn.model.Server;
import com.yn.model.Station;
import com.yn.model.TransactionRecord;
import com.yn.model.User;
import com.yn.model.Wallet;
import com.yn.service.ApolegamyOrderService;
import com.yn.service.ApolegamyService;
import com.yn.service.BankCardService;
import com.yn.service.BillOrderService;
import com.yn.service.BillWithdrawalsService;
import com.yn.service.ConstructionService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.service.StationService;
import com.yn.service.SystemConfigService;
import com.yn.service.TransactionRecordService;
import com.yn.service.UserService;
import com.yn.service.WalletService;
import com.yn.service.kftService.RechargeService;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewServer;
import com.yn.vo.OrderVo;
import com.yn.vo.RechargeVo;
import com.yn.vo.StationVo;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/test")
public class TestController {

	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	OrderPlanService orderPlanService;
	@Autowired
	private UserService userservice;
	@Autowired
	ConstructionService constructionService;
	@Autowired
	TransactionRecordService transactionRecordService;
	@Autowired
	private ServerService serverService;
	@Autowired
	WalletService  walletService;
	@Autowired
	BillOrderService  billOrderService;
	@Autowired
	ApolegamyService apolegamyService;
	@Autowired
	BankCardService bankCardService;
	@Autowired
	StationService  stationService;
	@Autowired
	ApolegamyOrderService APOservice;
	@Autowired
	OrderService orderService;
	@Autowired
	NewServerPlanService newserverPlanService;
	@Autowired
	SystemConfigService  systemConfigService;
	
	
	
	       @RequestMapping("/dotest") 
	       @ResponseBody
	       public Object helloJsp01(){  
	    	   Integer type = 1;

	   		String orderCode = "woxs171101141014";

	   		logger.info("---- ---- ---- ------ ----- 保存的订单号为：" + orderCode);
	   		Order orderSize = orderService.finByOrderCode(orderCode);

	   		Map<String, Long> map = new HashMap<String, Long>();
	   		
	   			logger.info("---- ---- ---- ------ ----- 开始生成订单");
	   			NewPlanVo plan = new  NewPlanVo();
	   			plan.setOrderCode("woxs171101141014");
	   			plan.setPhone("18820852129");
	   			plan.setAllMoney(330.0);
	   			plan.setNum(10.0);

	   			List<Long> listid = new ArrayList<Long>();
	   			listid.add(2l);
	   			List<Apolegamy> list = apolegamyService.findAll(listid);

	   			Double apoPrice = 0.0;

	   			for (Apolegamy apolegamy : list) {
	   				apoPrice += apolegamy.getPrice();
	   			}

	   			Long planid = 5L;

	   			NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

	   			newserverPlan.setMinPurchase(plan.getNum().doubleValue());

	   			User user02 = userservice.findByPhone(plan.getPhone());
	   			/** 添加订单*/
	   			Order order = newserverPlanService.getOrder(newserverPlan, user02, plan.getAllMoney(), apoPrice,
	   					plan.getOrderCode(), null,type);

	   			/** 取出订单号并添加*/
	   			order.setOrderCode(plan.getOrderCode());
	   			orderService.newSave(order);

	   			Order order02 = new Order();
	   			order02.setOrderCode(order.getOrderCode());

	   			Order neworder = orderService.findOne(order02);

	   			/** 订单计划表*/
	   			Long id = newserverPlan.getId();

	   			NewServerPlan serverPlan = newserverPlanService.findOne(id);

	   			OrderPlan orderPlan = newserverPlanService.giveOrderPlan(newserverPlan, neworder);

	   			OrderPlan orderPlan2 = new OrderPlan();
	   			orderPlan2.setOrderId(orderPlan.getOrderId());

	   			OrderPlan newOrdPlan = orderPlanService.findOne(orderPlan2);

	   			order.setOrderPlanId(newOrdPlan.getId());

	   			orderPlanService.save(orderPlan);

	   			neworder.setOrderPlan(newOrdPlan);

	   			/** 添加电站 */
	   			//stationService.insertStation(neworder);

	   			logger.info("---- ---- ------ ----- ----- 开始添加记录表");
	   			APOservice.getapole(neworder, listid);
	   			logger.info("---- ---- ------ ----- ----- 添加结束！");
	   			neworder.getUser().setPassword(null);

	   			map.put("orderId", neworder.getId());

	   			logger.info("---- ---- ---- ------ ----- 生成订单成功！");
	   			/** 传出电站的id */
	   			return ResultVOUtil.success(map);
	        
	       } 
	       
	       @ResponseBody
	       @RequestMapping("/test")  
	       public Object helloJsp001(RechargeVo rechargeVo){
	    	   
	    	   Order neworder = new Order();
	    	   neworder.setId(574L);
	    	   neworder.setYnApolegamyPrice(5.00);
	    	   
	    	   List<Long> list = new LinkedList<Long>();
	    	   list.add(0L);
	    	   
	    	   APOservice.getapole(neworder, list);
	    	   
	    	   
	              return ResultVOUtil.success(null);  
	       } 
	         
	       
	       
	       
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {
		 
		System.out.println("进入测试!");

			return ResultVOUtil.success(null);
	    }
	
}
