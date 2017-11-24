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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.exception.MyException;
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
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.MD5Util;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.utils.PropertyUtils;
import com.yn.utils.StringUtil;
import com.yn.vo.ApolegamyVo;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewServer;
import com.yn.vo.OrderVo;
import com.yn.vo.RechargeVo;
import com.yn.vo.StationVo;
import com.yn.vo.UserVo;
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
	       public Object helloJsp01(ApolegamyVo apolegamyVo){  
	    	   
	    	   NewServerPlan findOne = newserverPlanService.findOne(1l);
	           
	           Double minpur =	findOne.getMinPurchase().doubleValue();
	           
	           logger.info("------ ---- -- -- -- -- - -- - - 购买量为： "+8);
	           
	   	        if(null == minpur || minpur > 8 ){
	   	        	logger.info("------ ---- -- -- -- -- - -- - - 千瓦时不能为空且小于 "+findOne.getMinPurchase());
	   	        	
	   	        	return  ResultVOUtil.error(777, Constant.PUR_NULL+findOne.getMinPurchase());
	   	  	  	}
	           
	     
	   		return ResultVOUtil.success(null);
	        
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

				 Long userId = Long.valueOf(69);
	
					logger.info("---- ---- ---- ------ ----- 开始生成订单");

					List<Long> listid = new ArrayList<Long>();

					Double apoPrice = 0.0;

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
