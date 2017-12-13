package com.yn.web;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import com.yn.model.*;
import com.yn.service.*;
import com.yn.utils.BeanCopy;
import com.yn.vo.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yn.vo.ApolegamyVo;
import com.yn.vo.RechargeVo;
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
	@Autowired
	QualificationsService qualificationsService;

	@Autowired
	BillRefundService billRefundService;
	
	       @RequestMapping("/dotest") 
	       @ResponseBody
	       public Object helloJsp01(OrderVo orderVo){

			   orderVo.setId(804L);
			   orderVo.setStatus(4);

			   Order order = new Order();
			   BeanCopy.copyProperties(orderVo, order);

			   orderService.updateOrderbyId(order);

			   BillRefund billRefund = bankCardService.getBank(order.getId());
			   billRefund.setStatus(0);

			   billRefundService.save(billRefund);

			   transactionRecordService.InsertBillAll(billRefund);
	    	  
	   		return ResultVOUtil.success();
	        
	       } 
	       
	       @ResponseBody
	       @RequestMapping("/test")  
	       public Object helloJsp001(RechargeVo rechargeVo){
	    	   
	    	   Long userid = 7110L;


	   		User user = userservice.findOne(userid);

	   		user.setFullAddressText("东莞市");
	   		
	   		user.setPhone("18317829893");
	   		user.setUserName("无");

	   		userservice.save(user);
	    	   
	              return ResultVOUtil.success(null);  
	       } 
	         
	       
	       
	       
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {

				 Integer type = 0;

				 Long userId = Long.valueOf(100);
	
					logger.info("---- ---- ---- ------ ----- 开始生成订单");

					List<Long> listid = new ArrayList<Long>();

					Double apoPrice =117040.00;

					Long planid = 11L;

					NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

					User user02 = userservice.findOne(userId);
					
				    String orderCode =	serverService.getnewOrderCode(newserverPlan.getServerId(), user02.getProvinceId());
					
					/** 添加订单*/
					Order order = newserverPlanService.getnewOrder(newserverPlan, user02,0.0, apoPrice,
							orderCode, null,type);

					/** 取出订单号并添加*/
					order.setOrderCode(orderCode);
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
					stationService.insertStation(neworder);

					logger.info("---- ---- ------ ----- ----- 开始添加记录表");
					APOservice.getapole(neworder, listid);
					logger.info("---- ---- ------ ----- ----- 添加结束！");
					neworder.getUser().setPassword(null);

				
					return ResultVOUtil.success();
				}
	
}
